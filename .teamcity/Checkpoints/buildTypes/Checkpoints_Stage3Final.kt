package Checkpoints.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.finishBuildTrigger

object Checkpoints_Stage3Final : BuildType({
    name = "Stage 3 - Final"
    description = "Passes all QA stages"

    vcs {
        root(_Self.vcsRoots.GitHubEclipseBuildship)

        checkoutMode = CheckoutMode.ON_AGENT
    }

    triggers {
        finishBuildTrigger {
            buildTypeExtId = "${Checkpoints_Stage2IntegrationTests.id}"
            branchFilter = "+:*"
        }
    }

    dependencies {
        snapshot(Checkpoints_Stage1Distribution) {
        }
        snapshot(Checkpoints_Stage2IntegrationTests) {
            onDependencyFailure = FailureAction.CANCEL
            onDependencyCancel = FailureAction.CANCEL
        }
    }
})
