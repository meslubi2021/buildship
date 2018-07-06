package Buildship.Check.Checkpoints.buildTypes

import Buildship.GitHubVcsRoot
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.CheckoutMode
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.vcs

object Stage1BasicTestCoverage : BuildType({
    name = "Stage 1 - Basic test coverage"
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
            branchFilter = "-:teamcity-versioned-settings"
            perCheckinTriggering = true
            groupCheckinsByCommitter = true
            enableQueueOptimization = false
        }
    }

    dependencies {
        snapshot(Buildship.Check.Eclipse_Commit.Commit_Linux.buildTypes.Eclipse42) {
        }
        snapshot(Buildship.Check.Eclipse_Commit.Commit_Linux.buildTypes.Eclipse47) {
        }
        snapshot(Buildship.Check.Eclipse_Commit.Commit_Windows.buildTypes.Commit_Windows_Eclipse42Build) {
        }
        snapshot(Buildship.Check.Eclipse_Commit.Commit_Windows.buildTypes.Commit_Windows_Eclipse46Build) {
        }
    }
})
