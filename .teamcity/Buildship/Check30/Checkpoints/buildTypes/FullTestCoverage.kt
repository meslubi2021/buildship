package Buildship.Check30.Checkpoints.buildTypes

import Buildship.GitHubVcsRoot
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.CheckoutMode
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.finishBuildTrigger

object FullTestCoverage : BuildType({
    id("Checkpoint_Full_Test_Coverage_30")
    name = "Full Test Coverage"
    description = "Runs all integration tests"

    vcs {
        root(GitHubVcsRoot)
        checkoutMode = CheckoutMode.ON_AGENT
    }

    triggers {
        finishBuildTrigger {
            buildTypeExtId = "${BasicTestCoverage.id}"
            successfulOnly = true
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

        snapshot(Buildship.Check30.FullTestCoverage.Linux.buildTypes.Eclipse44, CheckpointUtils.DefaultFailureCondition)
        snapshot(Buildship.Check30.FullTestCoverage.Linux.buildTypes.Eclipse45, CheckpointUtils.DefaultFailureCondition)
        snapshot(Buildship.Check30.FullTestCoverage.Linux.buildTypes.Eclipse46, CheckpointUtils.DefaultFailureCondition)
        snapshot(Buildship.Check30.FullTestCoverage.Linux.buildTypes.Eclipse47Java9, CheckpointUtils.DefaultFailureCondition)
        snapshot(Buildship.Check30.FullTestCoverage.Linux.buildTypes.Eclipse48, CheckpointUtils.DefaultFailureCondition)
        snapshot(Buildship.Check30.FullTestCoverage.Linux.buildTypes.Eclipse410, CheckpointUtils.DefaultFailureCondition)
        snapshot(Buildship.Check30.FullTestCoverage.Windows.buildTypes.Eclipse44, CheckpointUtils.DefaultFailureCondition)
        snapshot(Buildship.Check30.FullTestCoverage.Windows.buildTypes.Eclipse47, CheckpointUtils.DefaultFailureCondition)
    }
})
