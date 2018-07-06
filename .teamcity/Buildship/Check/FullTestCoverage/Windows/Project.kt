package Buildship.Check.FullTestCoverage.Windows

import Buildship.Check.FullTestCoverage.Windows.buildTypes.Eclipse44
import Buildship.Check.FullTestCoverage.Windows.buildTypes.Eclipse47
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Full_Test_Coverage_Windows")
    name = "Full Test Coverage - Windows"

    buildType(Eclipse44)
    buildType(Eclipse47)
})
