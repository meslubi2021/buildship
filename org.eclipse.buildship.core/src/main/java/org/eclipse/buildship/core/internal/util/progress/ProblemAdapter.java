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

import org.gradle.tooling.events.problems.ProblemDescriptor;
import org.gradle.tooling.events.problems.Severity;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import org.eclipse.buildship.core.internal.gradle.FileLocation;
import org.eclipse.buildship.core.internal.util.gradle.Pair;

public class ProblemAdapter {

    private ProblemDescriptor problem;

    private ProblemAdapter(ProblemDescriptor pd) {
        this.problem = pd;
    }

    public static ProblemAdapter from(ProblemDescriptor pd) {
        return new ProblemAdapter(pd);
    }

    public ProblemDescriptor getProblem() {
        return this.problem;
    }

    public int toMarkerSeverity() {
        Severity severity = this.problem.getSeverity();
        if(severity == Severity.WARNING) {
            return IMarker.SEVERITY_WARNING;
        }
        if(severity == Severity.ADVICE) {
            return IMarker.SEVERITY_INFO;
        }
        if(severity == Severity.ERROR) {
            return IMarker.SEVERITY_ERROR;
        }
        return IMarker.SEVERITY_INFO;
    }

    public Optional<FileLocation> firstFileLocation() {
        return this.problem.getLocations().stream().filter(FileLocation.class::isInstance).map(FileLocation.class::cast).findFirst();
    }

    public Optional<Pair<IResource, Integer>> resourceAndFileNumberOfFirstFileLocation() {
        return firstFileLocation().map(location -> new Pair<>(toResource(location), lineNumberOf(location)));
    }

    private static IResource toResource(FileLocation location) {
        IPath absolutePath = Path.fromOSString(location.getPath());
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IFile workspacePath = workspaceRoot.getFileForLocation(absolutePath);
        if (workspacePath.exists()) {
            return workspacePath;
        }
        return ResourcesPlugin.getWorkspace().getRoot();
    }

    private static Integer lineNumberOf(FileLocation location) {
        if (location.getLine() != null) {
            return location.getLine();
        }
        return -1;
    }
}
