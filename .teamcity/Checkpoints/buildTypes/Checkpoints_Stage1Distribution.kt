package Checkpoints.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.vcs

object Checkpoints_Stage1Distribution : BuildType({
    name = "Stage 1 - Distribution"
    description = "Builds production distributions"

    vcs {
        root(_Self.vcsRoots.Git)

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
        snapshot(Commit_Linux.buildTypes.Commit_Eclipse42Build) {
        }
        snapshot(Commit_Linux.buildTypes.Commit_Eclipse47Build) {
        }
        snapshot(Commit_Windows.buildTypes.Commit_Windows_Eclipse42Build) {
        }
        snapshot(Commit_Windows.buildTypes.Commit_Windows_Eclipse46Build) {
        }
    }
})
