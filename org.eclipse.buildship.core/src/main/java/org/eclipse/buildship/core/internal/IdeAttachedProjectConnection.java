/*
 * Copyright (c) 2018 the original author or authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.buildship.core.internal;

import org.gradle.tooling.BuildAction;
import org.gradle.tooling.BuildActionExecuter;
import org.gradle.tooling.BuildActionExecuter.Builder;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.CancellationTokenSource;
import org.gradle.tooling.GradleConnectionException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.IntermediateResultHandler;
import org.gradle.tooling.LongRunningOperation;
import org.gradle.tooling.ModelBuilder;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.ResultHandler;
import org.gradle.tooling.TestLauncher;
import org.gradle.tooling.model.build.BuildEnvironment;

import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.buildship.core.internal.configuration.GradleArguments;
import org.eclipse.buildship.core.internal.gradle.GradleProgressAttributes;

final class IdeAttachedProjectConnection implements ProjectConnection {

    private final ProjectConnection delegate;
    private final GradleArguments gradleArguments;
    private final GradleProgressAttributes progressAttributes;

    private IdeAttachedProjectConnection(ProjectConnection connection, GradleArguments gradleArguments, GradleProgressAttributes progressAttributes) {
        this.delegate = connection;
        this.gradleArguments = gradleArguments;
        this.progressAttributes = progressAttributes;
    }

    @Override
    public BuildLauncher newBuild() {
        return configureOperation(this.delegate.newBuild());
    }

    @Override
    public TestLauncher newTestLauncher() {
        return configureOperation(this.delegate.newTestLauncher());
    }

    @Override
    public <T> ModelBuilder<T> model(Class<T> modelType) {
        return configureOperation(this.delegate.model(modelType));
    }

    @Override
    public <T> BuildActionExecuter<T> action(BuildAction<T> buildAction) {
        return configureOperation(this.delegate.action(buildAction));
    }

    private <T extends LongRunningOperation> T configureOperation(T operation) {
        BuildEnvironment buildEnvironment = this.delegate.getModel(BuildEnvironment.class);
        this.gradleArguments.applyTo(operation, buildEnvironment);
        this.progressAttributes.applyTo(operation);
        return operation;
    }

    @Override
    public void close() {
        this.delegate.close();
    }

    @Override
    public Builder action() {
        return new IdeAttachedBuilder(this.delegate.action());
    }

    @Override
    public <T> T getModel(Class<T> modelType) throws GradleConnectionException, IllegalStateException {
        return model(modelType).get();
    }

    @Override
    public <T> void getModel(Class<T> modelType, ResultHandler<? super T> handler) throws IllegalStateException {
        model(modelType).get(handler);
    }

    public static ProjectConnection newInstance(CancellationTokenSource tokenSource, GradleArguments gradleArguments, IProgressMonitor monitor) {
        GradleConnector connector = GradleConnector.newConnector();
        gradleArguments.applyTo(connector);
        ProjectConnection connection = connector.connect();

        GradleProgressAttributes progressAttributes = GradleProgressAttributes.builder(tokenSource, monitor)
                .forBackgroundProcess()
                .withFullProgress()
                .build();

        return new IdeAttachedProjectConnection(connection, gradleArguments, progressAttributes);
    }

    private class IdeAttachedBuilder implements Builder {

        private final Builder action;

        IdeAttachedBuilder(Builder action) {
            this.action = action;
        }

        @Override
        public <T> Builder projectsLoaded(BuildAction<T> buildAction, IntermediateResultHandler<? super T> handler) throws IllegalArgumentException {
            this.action.projectsLoaded(buildAction, handler);
            return this;
        }

        @Override
        public <T> Builder buildFinished(BuildAction<T> buildAction, IntermediateResultHandler<? super T> handler) throws IllegalArgumentException {
            this.action.buildFinished(buildAction, handler);
            return this;
        }

        @Override
        public BuildActionExecuter<Void> build() {
            return configureOperation(this.action.build());
        }
    }
}