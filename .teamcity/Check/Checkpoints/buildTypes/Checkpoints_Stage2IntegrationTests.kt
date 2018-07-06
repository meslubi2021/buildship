package Check.Checkpoints.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.CheckoutMode
import jetbrains.buildServer.configs.kotlin.v2018_1.FailureAction
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.finishBuildTrigger

object Checkpoints_Stage2IntegrationTests : BuildType({
    name = "Stage 2 - Integration Tests"
    description = "Passes integration tests"

    vcs {
        root(_Self.vcsRoots.GitHubEclipseBuildship)

        checkoutMode = CheckoutMode.ON_AGENT
    }

    triggers {
        finishBuildTrigger {
            buildTypeExtId = "${Checkpoints_Stage1Distribution.id}"
            successfulOnly = true
            branchFilter = "+:*"
        }
    }

    dependencies {
        snapshot(Checkpoints_Stage1Distribution) {
            onDependencyFailure = FailureAction.CANCEL
            onDependencyCancel = FailureAction.CANCEL
        }
        snapshot(IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_CompleteBuildLinuxEclipse48Java8) {
        }
        snapshot(IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_Eclipse42Build) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_Eclipse43Build) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_Eclipse44Build) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_Eclipse45Build) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_Eclipse46Build) {
        }
        snapshot(IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_Eclipse47IntegrationTests) {
        }
        snapshot(IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_Eclipse47Java9) {
            onDependencyFailure = FailureAction.IGNORE
            onDependencyCancel = FailureAction.IGNORE
        }
        snapshot(IntegrationTests_Windows.buildTypes.IntegrationTests_Windows_Eclipse44Build) {
        }
        snapshot(IntegrationTests_Windows.buildTypes.IntegrationTests_Windows_Eclipse47IntegrationTestsWindows) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }
})
