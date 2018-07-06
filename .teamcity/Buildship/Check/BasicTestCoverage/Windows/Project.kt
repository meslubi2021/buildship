package Buildship.Check.BasicTestCoverage.Windows

import Buildship.Check.BasicTestCoverage.Windows.buildTypes.Eclipse42
import Buildship.Check.BasicTestCoverage.Windows.buildTypes.Eclipse46

import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Basic_Test_Coverage_Windows")
    name = "Basic Test Coverage - Windows"

    buildType(Eclipse42)
    buildType(Eclipse46)

    buildTypesOrder = arrayListOf(Eclipse42, Eclipse46)
})