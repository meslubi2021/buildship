package Buildship.Check.BasicTestCoverage

import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Basic_Test_Coverage")
    name = "Basic Test Coverage"

    subProject(Buildship.Check.BasicTestCoverage.Linux.Project)
    subProject(Buildship.Check.BasicTestCoverage.Windows.Project)
})
