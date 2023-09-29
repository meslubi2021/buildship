/*******************************************************************************
 * Copyright (c) 2023 Gradle Inc. and others
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.buildship.core.internal.gradle;


public class TaskLocation implements ProblemLocation {

    private final String identityPath;

    public TaskLocation(String identityPath) {
        this.identityPath = identityPath;
    }


    public String getIdentityPath() {
        return this.identityPath;
    }
}
