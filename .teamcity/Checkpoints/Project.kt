package Checkpoints

import Checkpoints.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Checkpoints")
    name = "Checkpoints"
    description = "Configurations of the stages to getting a distribution that passes QA"

    buildType(Checkpoints_Stage2IntegrationTests)
    buildType(Checkpoints_Stage1Distribution)
    buildType(Checkpoints_Stage3Final)
})
