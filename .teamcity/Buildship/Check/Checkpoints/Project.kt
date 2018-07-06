package Buildship.Check.Checkpoints

import Buildship.Check.Checkpoints.buildTypes.Stage1BasicTestCoverage
import Buildship.Check.Checkpoints.buildTypes.Stage2FullTestCoverage
import Buildship.Check.Checkpoints.buildTypes.Stage3Final
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Checkpoints")
    name = "Checkpoints"
    description = "Configurations of the stages to getting a distribution that passes QA"

    buildType(Stage1BasicTestCoverage)
    buildType(Stage2FullTestCoverage)
    buildType(Stage3Final)
})
