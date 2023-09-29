package org.eclipse.buildship.core.internal.util.progress;

import java.util.Optional;

import org.gradle.tooling.events.ProgressEvent;
import org.gradle.tooling.events.ProgressListener;
import org.gradle.tooling.events.problems.ProblemEvent;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;

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
            ProblemAdapter problem = ProblemAdapter.from(ProblemParser.parse(problemJson));
            Optional<Pair<IResource,Integer>> location = problem.resourceAndFileNumberOfFirstFileLocation();
            if (location.isPresent()) {
                GradleErrorMarker.createMarker(problem.toMarkerSeverity(), location.get().getFirst(), this.gradleBuild, problem.getProblem().getLabel(), null, location.get().getSecond());
            } else {
                GradleErrorMarker.createMarker(problem.toMarkerSeverity(),  ResourcesPlugin.getWorkspace().getRoot(), this.gradleBuild, problem.getProblem().getLabel(), null, -1);
            }
        }
    }
}
