package org.eclipse.buildship.core.internal.util.progress;

import org.gradle.tooling.events.ProgressEvent;
import org.gradle.tooling.events.ProgressListener;
import org.gradle.tooling.events.problems.ProblemEvent;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.eclipse.core.resources.ResourcesPlugin;

import org.eclipse.buildship.core.internal.marker.GradleErrorMarker;
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
            String problemAsJson = problemEvent.getDescriptor().getJson();
            JsonElement element = JsonParser.parseString(problemAsJson);
            Problem problem = new Gson().fromJson(element, Problem.class);
            GradleErrorMarker.createWarning(ResourcesPlugin.getWorkspace().getRoot(), this.gradleBuild, problem.getLabel(), null, 0);
        }
    }

    private static class Problem {

        private String label;

        public String getLabel() {
            return this.label;
        }

        @SuppressWarnings("unused")
        public void setLabel(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return "Problem [label=" + this.label + "]";
        }

    }
}
