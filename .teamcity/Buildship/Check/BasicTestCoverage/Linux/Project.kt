package Buildship.Check.BasicTestCoverage.Linux

import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Basic_Test_Coverage_Linux")
    name = "Basic Test Coverage - Linux"
    description = "Configurations that provide basic test coverage"

    buildType(Buildship.Check.BasicTestCoverage.Linux.buildTypes.Eclipse42)
    buildType(Buildship.Check.BasicTestCoverage.Linux.buildTypes.Eclipse47)
})
