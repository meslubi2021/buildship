package Check.Checkpoints

import Check.Checkpoints.buildTypes.Checkpoints_Stage1Distribution
import Check.Checkpoints.buildTypes.Checkpoints_Stage2IntegrationTests
import Check.Checkpoints.buildTypes.Checkpoints_Stage3Final
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Check/Checkpoints")
    name = "Check/Checkpoints"
    description = "Configurations of the stages to getting a distribution that passes QA"

    buildType(Checkpoints_Stage2IntegrationTests)
    buildType(Checkpoints_Stage1Distribution)
    buildType(Checkpoints_Stage3Final)
})
