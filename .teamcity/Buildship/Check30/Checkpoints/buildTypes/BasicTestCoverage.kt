package Buildship.Check30.Checkpoints.buildTypes

import Buildship.GitHubVcsRoot
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.CheckoutMode
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.vcs

object BasicTestCoverage : BuildType({
    id("Checkpoint_Basic_Test_Coverage_30")
    name = "Basic Test Coverage"
    description = "Runs basic integration tests"

    vcs {
        root(GitHubVcsRoot)
        checkoutMode = CheckoutMode.ON_AGENT
    }

    triggers {
        vcs {
            quietPeriodMode = VcsTrigger.QuietPeriodMode.USE_DEFAULT
            triggerRules = """
                +:**
                -:**.md
            """.trimIndent()
            branchFilter = """
                -:teamcity-versioned-settings
                +:*
            """.trimIndent()
            perCheckinTriggering = true
            groupCheckinsByCommitter = true
            enableQueueOptimization = false
        }
    }

    dependencies {
        snapshot(Buildship.Check30.BasicTestCoverage.Linux.buildTypes.Eclipse42, CheckpointUtils.DefaultFailureCondition)
        snapshot(Buildship.Check30.BasicTestCoverage.Linux.buildTypes.Eclipse47, CheckpointUtils.DefaultFailureCondition)
        snapshot(Buildship.Check30.BasicTestCoverage.Windows.buildTypes.Eclipse42, CheckpointUtils.DefaultFailureCondition)
        snapshot(Buildship.Check30.BasicTestCoverage.Windows.buildTypes.Eclipse46, CheckpointUtils.DefaultFailureCondition)
    }
})
