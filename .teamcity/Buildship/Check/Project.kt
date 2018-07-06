package Buildship.Check

import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Check")
    name = "Check"
    description = "Test coverage"
    subProjectsOrder = arrayListOf(Buildship.Check.Checkpoints.Project,
                                   Buildship.Check.Eclipse_Commit.Project,
                                   Buildship.Check.BasicIntegrationTests.Project,
                                   Buildship.Check.CrossVersionCoverage.Project,
                                   Buildship.Check.Eclipse_Experimental.Project)

    subProject(Buildship.Check.Checkpoints.Project)
    subProject(Buildship.Check.Eclipse_Commit.Project)
    subProject(Buildship.Check.BasicIntegrationTests.Project)
    subProject(Buildship.Check.CrossVersionCoverage.Project)
    subProject(Buildship.Check.Eclipse_Experimental.Project)
})
