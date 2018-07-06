package Buildship.Check.Checkpoints

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
