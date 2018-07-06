package Check.Checkpoints.buildTypes

import _Self.buildTypes.CheckpointTemplate
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.FailureAction
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.finishBuildTrigger

object Stage3Final : BuildType({
    name = "Stage 3 - Final"
    description = "Passes all QA stages"

    templates(CheckpointTemplate)

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
