package Buildship.Check30.BasicTestCoverage

import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Basic_Test_Coverage_30")
    name = "Basic Test Coverage"

    subProject(Buildship.Check30.BasicTestCoverage.Linux.Project)
    subProject(Buildship.Check30.BasicTestCoverage.Windows.Project)
})
