package Buildship.Check30.Checkpoints.buildTypes

import Buildship.GitHubVcsRoot
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.CheckoutMode
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.finishBuildTrigger

object Final : BuildType({
    id("Checkpoint_Final_30")
    name = "Final"
    description = "Passes all QA stages"

    vcs {
        root(GitHubVcsRoot)
        checkoutMode = CheckoutMode.ON_AGENT
    }

    triggers {
        finishBuildTrigger {
            buildTypeExtId = "${FullTestCoverage.id}"
            branchFilter = """
                +:*
            """.trimIndent()
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux")
    }

    dependencies {
        snapshot(BasicTestCoverage, CheckpointUtils.DefaultFailureCondition)
        snapshot(FullTestCoverage, CheckpointUtils.DefaultFailureCondition)
    }
})
