package Check

import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Check")
    name = "Check"
    description = "Test coverage"
    subProjectsOrder = arrayListOf(Check.Checkpoints.Project,
                                   Check.Checkpoints.Eclipse_Commit.Project,
                                   BasicIntegrationTests.Project,
                                   CrossVersionCoverage.Project,
                                   Eclipse_Experimental.Project)

    subProject(Check.Checkpoints.Project)
    subProject(Check.Checkpoints.Eclipse_Commit.Project)
    subProject(BasicIntegrationTests.Project)
    subProject(CrossVersionCoverage.Project)
    subProject(Eclipse_Experimental.Project)
})
