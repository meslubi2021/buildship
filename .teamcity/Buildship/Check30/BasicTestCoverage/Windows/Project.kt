package Buildship.Check30.BasicTestCoverage.Windows

import Buildship.Check30.BasicTestCoverage.Windows.buildTypes.Eclipse42
import Buildship.Check30.BasicTestCoverage.Windows.buildTypes.Eclipse46

import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Basic_Test_Coverage_Windows_30")
    name = "Basic Test Coverage - Windows"

    buildType(Eclipse42)
    buildType(Eclipse46)

    buildTypesOrder = arrayListOf(Eclipse42, Eclipse46)
})