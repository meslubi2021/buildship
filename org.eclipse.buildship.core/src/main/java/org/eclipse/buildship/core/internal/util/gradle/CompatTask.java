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

import org.gradle.tooling.model.GradleProject;
import org.gradle.tooling.model.GradleTask;
import org.gradle.tooling.model.ProjectIdentifier;

/**
 * Compatibility decorator for {@link GradleTask}.
 *
 * @author Donat Csikos
 */
class CompatTask implements GradleTask {

    private static final String DEFAULT_DESCRIPTION = "";
    private static final String DEFAULT_GROUP_NAME = "other";

    private final GradleTask delegate;

    public CompatTask(GradleTask gradleTask) {
        this.delegate = gradleTask;
    }

    @Override
    public String getDescription() {
        try {
            String description = this.delegate.getDescription();
            return description == null ? DEFAULT_DESCRIPTION : description;
        } catch (Exception e) {
            return DEFAULT_DESCRIPTION;
        }
    }

    @Override
    public String getDisplayName() {
        return this.delegate.getDisplayName();
    }

    /**
     * If Gradle versions >= 2.5 or if group name is null then returns "other".
     */
    @Override
    public String getGroup() {
        try {
            String group = this.delegate.getGroup();
            return group == null ? DEFAULT_GROUP_NAME : group;
        } catch (Exception e) {
            return DEFAULT_GROUP_NAME;
        }
    }

    @Override
    public String getName() {
        return this.delegate.getName();
    }

    @Override
    public String getPath() {
        return this.delegate.getPath();
    }

    @Override
    public GradleProject getProject() {
        return this.delegate.getProject();
    }

    @Override
    public ProjectIdentifier getProjectIdentifier() {
        return this.delegate.getProjectIdentifier();
    }

    /**
     *
     */
    @Override
    public boolean isPublic() {
        // returns true for Gradle versions < 2.1
        try {
            return this.delegate.isPublic();
        } catch (Exception ignore) {
            return true;
        }
    }

    @Override
    public String getBuildTreePath() {
        // returns true for Gradle versions < 2.1
        try {
            return this.delegate.getBuildTreePath();
        } catch (Exception ignore) {
            return getPath();
        }
    }

}
