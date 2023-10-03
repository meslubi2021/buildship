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

import java.util.Optional;

import org.gradle.tooling.events.ProgressEvent;
import org.gradle.tooling.events.ProgressListener;
import org.gradle.tooling.events.problems.ProblemEvent;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;

import org.eclipse.buildship.core.internal.CorePlugin;
import org.eclipse.buildship.core.internal.gradle.Problem;
import org.eclipse.buildship.core.internal.gradle.ProblemParser;
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
            String problemJson = problemEvent.getDescriptor().getJson();
            try {
                reportProblemWithEclipseMarker(problemJson);
            } catch (Exception e) {
                CorePlugin.logger().warn("Cannot report problem " + problemJson, e);
            }
        }
    }

    private void reportProblemWithEclipseMarker(String problemJson) {
        Problem gradleProblem = ProblemParser.parse(problemJson);
        ProblemAdapter eclipseProblem = ProblemAdapter.from(gradleProblem);
        Optional<Pair<IResource,Integer>> location = eclipseProblem.resourceAndFileNumberOfFirstFileLocation();
        if (location.isPresent()) {
            GradleErrorMarker.createMarker(eclipseProblem.toMarkerSeverity(), location.get().getFirst(), this.gradleBuild, eclipseProblem.getProblem().getLabel(), null, location.get().getSecond(), gradleProblem.getCategory(), gradleProblem.getSolutions(), gradleProblem.getDocumentationLink());
        } else {
            GradleErrorMarker.createMarker(eclipseProblem.toMarkerSeverity(),  ResourcesPlugin.getWorkspace().getRoot(), this.gradleBuild, eclipseProblem.getProblem().getLabel(), null, -1, gradleProblem.getCategory(), gradleProblem.getSolutions(), gradleProblem.getDocumentationLink());
        }
    }
}
