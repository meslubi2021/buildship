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


public class ProblemCategory {

    private final String category;

    // TODO adapt new categorization scheme
    public ProblemCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }
}
