/*******************************************************************************
 * Copyright (c) 2023 Gradle Inc. and others
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.buildship.core.internal.util.progress;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.gradle.tooling.events.ProgressEvent;
import org.gradle.tooling.events.ProgressListener;
import org.gradle.tooling.events.problems.BaseProblemDescriptor;
import org.gradle.tooling.events.problems.ProblemAggregationDescriptor;
import org.gradle.tooling.events.problems.ProblemDescriptor;
import org.gradle.tooling.events.problems.ProblemEvent;
import org.gradle.tooling.events.problems.Solution;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;

import org.eclipse.buildship.core.internal.marker.GradleErrorMarker;
import org.eclipse.buildship.core.internal.util.gradle.Pair;
import org.eclipse.buildship.core.internal.workspace.InternalGradleBuild;

public class ProblemsReportingProgressListener implements ProgressListener {

    private InternalGradleBuild gradleBuild;

    public ProblemsReportingProgressListener(InternalGradleBuild gradleBuild) {
        this.gradleBuild = gradleBuild;
    }

    @Override
    public void statusChanged(ProgressEvent event) {
        if (event instanceof ProblemEvent) {
            ProblemEvent problemEvent = (ProblemEvent) event;
            BaseProblemDescriptor descriptor = problemEvent.getDescriptor();
            if (descriptor instanceof ProblemDescriptor) {
                ProblemDescriptor pd = (ProblemDescriptor) descriptor;
                reportProblemWithEclipseMarker(pd);
            }
            if (descriptor instanceof ProblemAggregationDescriptor) {
                ProblemAggregationDescriptor pad = (ProblemAggregationDescriptor) descriptor;
                pad.getAggregations().forEach(aggregation -> aggregation.getProblemDescriptors().forEach(pd -> reportProblemWithEclipseMarker(pd)));
            }
        }
    }

    private void reportProblemWithEclipseMarker(ProblemDescriptor pd) {
        ProblemAdapter eclipseProblem = ProblemAdapter.from(pd);
        Optional<Pair<IResource,Integer>> location = eclipseProblem.resourceAndFileNumberOfFirstFileLocation();
        List<String> solutions = pd.getSolutions().stream().map(Solution::toString).collect(toList());
        Optional<String> documentationLink = Optional.of(pd.getDocumentationLink().getUrl());
        String label = eclipseProblem.getProblem().getLabel().getLabel();
        if (location.isPresent()) {
            GradleErrorMarker.createMarker(
                    eclipseProblem.toMarkerSeverity(),
                    location.get().getFirst(),
                    this.gradleBuild,
                    label,
                    null,
                    location.get().getSecond(),
                    pd.getCategory().getCategory(),
                    solutions,
                    documentationLink);
        } else {
            GradleErrorMarker.createMarker(
                    eclipseProblem.toMarkerSeverity(),
                    ResourcesPlugin.getWorkspace().getRoot(),
                    this.gradleBuild,
                    label,
                    null,
                    -1,
                    pd.getCategory().getCategory(),
                    solutions,
                    documentationLink);
        }
    }
}
