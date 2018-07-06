package Buildship.Check.FullTestCoverage

import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Full_Test_Coverage")
    name = "Full Test Coverage"
    description = "Configurations that provide full test coverage"

    subProject(Buildship.Check.FullTestCoverage.Linux.Project)
    subProject(Buildship.Check.FullTestCoverage.Windows.Project)
})
