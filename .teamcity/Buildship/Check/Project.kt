package Buildship.Check

import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Check")
    name = "Check"
    description = "Test coverage"
    subProjectsOrder = arrayListOf(Buildship.Check.Checkpoints.Project,
                                   Buildship.Check.BasicTestCoverage.Project,
                                   Buildship.Check.FullTestCoverage.Project,
                                   Buildship.Check.CrossVersionCoverage.Project,
                                   Buildship.Check.Experimental.Project)

    subProject(Buildship.Check.Checkpoints.Project)
    subProject(Buildship.Check.BasicTestCoverage.Project)
    subProject(Buildship.Check.FullTestCoverage.Project)
    subProject(Buildship.Check.CrossVersionCoverage.Project)
    subProject(Buildship.Check.Experimental.Project)
})
