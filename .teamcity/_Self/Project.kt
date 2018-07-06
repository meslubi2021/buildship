package _Self

import _Self.buildTypes.Eclipse_Build
import _Self.vcsRoots.GitHubEclipseBuildship
import jetbrains.buildServer.configs.kotlin.v2018_1.ParameterDisplay
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    description = "Eclipse plugins for Gradle    http://eclipse.org/buildship"

    vcsRoot(GitHubEclipseBuildship)

    template(Eclipse_Build)

    params {
        password("eclipse.downloadServer.password", "credentialsJSON:3b651471-b495-4e6e-9708-f2d7860c90f7", label = "Password", display = ParameterDisplay.HIDDEN)
        param("env.JAVA_TOOL_OPTIONS", "-Dfile.encoding=UTF8")
        password("eclipse.downloadServer.username", "credentialsJSON:23f3947f-45b2-46b8-83f6-9341c9b914f6", label = "Username", display = ParameterDisplay.HIDDEN)
    }

    features {
        feature {
            id = "PROJECT_EXT_67"
            type = "project-graphs"
            param("series", """
                [
                  {
                    "type": "valueType",
                    "title": "Build Duration (excluding Checkout Time)",
                    "sourceBuildTypeId": "Tooling_Buildship_Commit_Eclipse42Build",
                    "key": "BuildDurationNetTime"
                  }
                ]
            """.trimIndent())
            param("format", "text")
            param("title", "Commit build time")
            param("seriesTitle", "Serie")
        }
    }

    cleanup {
        all(days = 5)
        history(days = 5)
        artifacts(days = 5)
        preventDependencyCleanup = false
    }

    subProject(Check.Promotion.Project)
    subProject(Check.Project)
})
