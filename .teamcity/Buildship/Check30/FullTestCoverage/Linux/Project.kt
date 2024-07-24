package Buildship.Check30.FullTestCoverage.Linux

import Buildship.Check30.FullTestCoverage.Linux.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2018_2.Project

object Project : Project({
    id("Full_Test_Coverage_Linux_30")
    name = "Full Test Coverage - Linux"

    buildType(Eclipse44)
    buildType(Eclipse45)
    buildType(Eclipse46)
    buildType(Eclipse47)
    buildType(Eclipse47Java9)
    buildType(Eclipse48)
    buildType(Eclipse49)
    buildType(Eclipse410)
})
