package Buildship.Check.Checkpoints.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.CheckoutMode
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.vcs

object Stage1Distribution : BuildType({
    name = "Stage 1 - Distribution"
    description = "Builds production distributions"

    vcs {
        root(_Self.vcsRoots.GitHubEclipseBuildship)
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
        snapshot(Buildship.Check.Eclipse_Commit.Commit_Linux.buildTypes.Commit_Eclipse42Build) {
        }
        snapshot(Buildship.Check.Eclipse_Commit.Commit_Linux.buildTypes.Commit_Eclipse47Build) {
        }
        snapshot(Buildship.Check.Eclipse_Commit.Commit_Windows.buildTypes.Commit_Windows_Eclipse42Build) {
        }
        snapshot(Buildship.Check.Eclipse_Commit.Commit_Windows.buildTypes.Commit_Windows_Eclipse46Build) {
        }
    }
})
