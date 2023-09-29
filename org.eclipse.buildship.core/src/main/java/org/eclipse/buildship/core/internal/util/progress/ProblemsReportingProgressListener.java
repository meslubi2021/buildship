package org.eclipse.buildship.core.internal.util.progress;

import java.util.Set;

import org.gradle.tooling.events.ProgressEvent;
import org.gradle.tooling.events.ProgressListener;
import org.gradle.tooling.events.problems.ProblemEvent;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

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
            System.out.println(problemAsJson);

            JsonElement element = JsonParser.parseString(problemAsJson);
            Problem problem = new Gson().fromJson(element, Problem.class);

            GradleErrorMarker.createMarker(severityOf(problem), resourceOf(problem), this.gradleBuild, problem.getLabel(), null, lineNumberOf(problem));
        }
    }

    // TODO only the first location is returned here; we should consider the case with multiple file locations
    private static IResource resourceOf(Problem problem) {
        for (ProblemLocation location : problem.getWhere()) {
            if (location.getPath() != null) {
                IPath absolutePath = Path.fromOSString(location.getPath());
                IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
                IFile workspacePath = workspaceRoot.getFileForLocation(absolutePath);
                if (workspacePath.exists()) {
                    return workspacePath;
                }
            }
        }
        return ResourcesPlugin.getWorkspace().getRoot();
    }

    private static Integer lineNumberOf(Problem problem) {
        for (ProblemLocation location : problem.getWhere()) {
            if (location.getLine() != null) {
                return location.getLine();
            }
        }
        return 0;
    }

    private static int severityOf(Problem problem) {
        String severity = problem.getSeverity();
        if (severity.equals("WARNING")) {
            return IMarker.SEVERITY_WARNING;
        } else if (severity.equals("ADVICE")) {
            return IMarker.SEVERITY_INFO;
        } else if (severity.equals("ERROR")) {
            return IMarker.SEVERITY_ERROR;
        } else {
            return IMarker.SEVERITY_INFO;
        }
    }

    private static class Problem {

        private String label;
        private String severity;
        private Set<ProblemLocation> where;

        public String getLabel() {
            return this.label;
        }

        @SuppressWarnings("unused")
        public void setLabel(String label) {
            this.label = label;
        }

        public String getSeverity() {
            return this.severity;
        }

        @SuppressWarnings("unused")
        public void setSeverity(String severity) {
            this.severity = severity;
        }


        public Set<ProblemLocation> getWhere() {
            return this.where;
        }

        public void setWhere(Set<ProblemLocation> where) {
            this.where = where;
        }

        @Override
        public String toString() {
            return "Problem [label=" + this.label + ", severity=" + this.severity + ", where=" + this.where + "]";
        }


    }

    private static class ProblemLocation {
        String path;
        Integer line;
        Integer length;

        public String getPath() {
            return this.path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Integer getLine() {
            return this.line;
        }

        public void setLine(Integer line) {
            this.line = line;
        }

        public Integer getLength() {
            return this.length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        @Override
        public String toString() {
            return "ProblemLocation [path=" + this.path + ", line=" + this.line + ", length=" + this.length + "]";
        }


    }
}
