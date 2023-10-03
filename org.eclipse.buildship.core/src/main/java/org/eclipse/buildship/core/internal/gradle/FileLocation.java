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

public class FileLocation implements ProblemLocation {

    private final String path;
    private final Integer line;
    private final Integer column;
    private final Integer length;

    public FileLocation(String path, Integer line, Integer column, Integer length) {
        this.path = path;
        this.line = line;
        this.column = column;
        this.length = length;
    }

    public String getPath() {
        return this.path;
    }

    public Integer getLine() {
        return this.line;
    }

    public Integer getColumn() {
        return this.column;
    }

    public Integer getLength() {
        return this.length;
    }
}
