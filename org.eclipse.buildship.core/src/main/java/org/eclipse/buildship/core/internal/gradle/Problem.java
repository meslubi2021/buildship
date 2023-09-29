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

import java.util.List;
import java.util.Set;

/**
 * Represents a problem reported via Gradle's Problems service.
 */
public class Problem {

    private final String label;
    private final ProblemSeverity severity;
    private final Set<ProblemLocation> locations;
    private final String documentationLink;
    private final List<String> solutions;
    private final ProblemCategory category;

    public Problem(
            String label,
            ProblemSeverity severity,
            Set<ProblemLocation> locations,
            String documentationLink, // TODO do we want only one link?
            List<String> solutions,
            // TODO how to represent exceptions? List<StacktraceElement?
            ProblemCategory category
            // TODO how to represent additional data? Map<String, Object>?
            ) {
                this.label = label;
                this.severity = severity;
                this.locations = locations;
                this.documentationLink = documentationLink;
                this.solutions = solutions;
                this.category = category;
    }

    public String getLabel() {
        return this.label;
    }

    public ProblemSeverity getSeverity() {
        return this.severity;
    }

    public Set<ProblemLocation> getLocations() {
        return this.locations;
    }

    public String getDocumentationLink() {
        return this.documentationLink;
    }

    public List<String> getSolutions() {
        return this.solutions;
    }

    public ProblemCategory getCategory() {
        return this.category;
    }
}
