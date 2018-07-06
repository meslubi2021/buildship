package Buildship.Check.Checkpoints.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.CheckoutMode
import jetbrains.buildServer.configs.kotlin.v2018_1.FailureAction
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.finishBuildTrigger

object Stage3Final : BuildType({
    name = "Stage 3 - Final"
    description = "Passes all QA stages"

    vcs {
        root(_Self.vcsRoots.GitHubEclipseBuildship)
        checkoutMode = CheckoutMode.ON_AGENT
    }

    triggers {
        finishBuildTrigger {
            buildTypeExtId = "${Stage2IntegrationTests.id}"
            branchFilter = "+:*"
        }
    }

    dependencies {
        snapshot(Stage1Distribution) {
        }
        snapshot(Stage2IntegrationTests) {
            onDependencyFailure = FailureAction.CANCEL
            onDependencyCancel = FailureAction.CANCEL
        }
    }
})
