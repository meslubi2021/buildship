package Check

import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Check")
    name = "Check"
    description = "Test coverage"
    subProjectsOrder = arrayListOf(RelativeId("Checkpoints"), RelativeId("Eclipse_Commit"), RelativeId("BasicIntegrationTests"))

    subProject(CrossVersionCoverage.Project)
    subProject(BasicIntegrationTests.Project)
    subProject(Checkpoints.Project)
    subProject(Eclipse_Commit.Project)
    subProject(Eclipse_Experimental.Project)
})
