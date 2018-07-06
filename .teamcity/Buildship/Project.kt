package Buildship

import jetbrains.buildServer.configs.kotlin.v2018_1.FailureAction
import jetbrains.buildServer.configs.kotlin.v2018_1.ParameterDisplay
import jetbrains.buildServer.configs.kotlin.v2018_1.Project
import jetbrains.buildServer.configs.kotlin.v2018_1.SnapshotDependency

object Project : Project({
    description = "Eclipse plugins for Gradle http://eclipse.org/buildship"

    vcsRoot(GitHubVcsRoot)

    template(EclipseBuildTemplate)

    params {
        password("eclipse.downloadServer.password", "credentialsJSON:3b651471-b495-4e6e-9708-f2d7860c90f7", label = "Password", display = ParameterDisplay.HIDDEN)
        param("env.JAVA_TOOL_OPTIONS", "-Dfile.encoding=UTF8")
        password("eclipse.downloadServer.username", "credentialsJSON:23f3947f-45b2-46b8-83f6-9341c9b914f6", label = "Username", display = ParameterDisplay.HIDDEN)
    }

    cleanup {
        all(days = 5)
        history(days = 5)
        artifacts(days = 5)
        preventDependencyCleanup = false
    }

    subProject(Buildship.Promotion.Project)
    subProject(Buildship.Check.Project)
})
