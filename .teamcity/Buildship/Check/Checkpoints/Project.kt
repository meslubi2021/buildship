package Buildship.Check.Checkpoints

import Buildship.Check.Checkpoints.buildTypes.BasicTestCoverage
import Buildship.Check.Checkpoints.buildTypes.FullTestCoverage
import Buildship.Check.Checkpoints.buildTypes.Final
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Checkpoints")
    name = "Checkpoints"
    description = "Configurations of the stages to getting a distribution that passes QA"

    buildType(BasicTestCoverage)
    buildType(FullTestCoverage)
    buildType(Final)

    buildTypesOrder = arrayListOf(BasicTestCoverage, FullTestCoverage, Final)
})
