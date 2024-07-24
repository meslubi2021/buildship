/*******************************************************************************
 * Copyright (c) 2023 Gradle Inc. and others
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.buildship.core.internal.util.gradle;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.google.common.collect.ImmutableList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.InstanceScope;

import org.eclipse.buildship.core.internal.CorePlugin;
import org.eclipse.buildship.core.internal.util.gradle.PublishedGradleVersions.LookupStrategy;

/**
 * Wraps the {@link PublishedGradleVersions} functionality in a background job that handles all
 * exceptions gracefully. If an exception occurs while calling the underlying
 * {@link PublishedGradleVersions} instance, default version information is provided. This handles,
 * for example, those scenarios where the versions cannot be retrieved because the user is behind a
 * proxy or offline.
 */
public final class PublishedGradleVersionsWrapper {

    private final AtomicReference<PublishedGradleVersions> publishedGradleVersions;

    public PublishedGradleVersionsWrapper() {
        this.publishedGradleVersions = new AtomicReference<>();
        new LoadVersionsJob().schedule();
    }

    public List<GradleVersion> getVersions() {
        PublishedGradleVersions versions = this.publishedGradleVersions.get();
        return versions != null ? versions.getVersions() : ImmutableList.of(GradleVersion.current());
    }

    /**
     * Loads the published Gradle versions in the background.
     * @author Stefan Oehme
     */
    private final class LoadVersionsJob extends Job {

        public LoadVersionsJob() {
            super("Loading available Gradle versions");
            setSystem(true);
            setRule(new ISchedulingRule() {

                @Override
                public boolean isConflicting(ISchedulingRule rule) {
                    return rule == this;
                }

                @Override
                public boolean contains(ISchedulingRule rule) {
                    return rule == this;
                }
            });
        }

        @Override
        protected IStatus run(IProgressMonitor monitor) {
            try {
                PublishedGradleVersions versions = PublishedGradleVersions.create(getLookupStrategy());
                PublishedGradleVersionsWrapper.this.publishedGradleVersions.set(versions);
            } catch (RuntimeException e) {
                CorePlugin.logger().warn("Could not load Gradle version information", e);
            }
            return Status.OK_STATUS;
        }

        @Override
        protected void canceling() {
            Thread.currentThread().interrupt();
        }

        private LookupStrategy getLookupStrategy() {
            String strategyName = InstanceScope.INSTANCE.getNode(CorePlugin.PLUGIN_ID).get("publishedgradleversions.lookupstrategy", LookupStrategy.REMOTE_IF_NOT_CACHED.name());
            try {
                return LookupStrategy.valueOf(strategyName);
            } catch (IllegalArgumentException e) {
                CorePlugin.logger().warn("Illegal published Gradle version lookup strategy: " + strategyName);
                return LookupStrategy.REMOTE_IF_NOT_CACHED;
            }
        }

    }

}
