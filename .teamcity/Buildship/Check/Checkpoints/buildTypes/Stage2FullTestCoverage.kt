package Buildship.Check.Checkpoints.buildTypes

import Buildship.GitHubVcsRoot
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.CheckoutMode
import jetbrains.buildServer.configs.kotlin.v2018_1.FailureAction
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.finishBuildTrigger

object Stage2FullTestCoverage : BuildType({
    name = "Stage 2 - Full Test Coverage"
    description = "Runs all integration tests"

    vcs {
        root(GitHubVcsRoot)
        checkoutMode = CheckoutMode.ON_AGENT
    }

    triggers {
        finishBuildTrigger {
            buildTypeExtId = "${Stage1BasicTestCoverage.id}"
            successfulOnly = true
            branchFilter = "+:*"
        }
    }

    dependencies {
        snapshot(Stage1BasicTestCoverage) {
            onDependencyFailure = FailureAction.CANCEL
            onDependencyCancel = FailureAction.CANCEL
        }
        snapshot(Buildship.Check.BasicIntegrationTests.IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_CompleteBuildLinuxEclipse48Java8) {
        }
        snapshot(Buildship.Check.BasicIntegrationTests.IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_Eclipse42Build) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(Buildship.Check.BasicIntegrationTests.IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_Eclipse43Build) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(Buildship.Check.BasicIntegrationTests.IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_Eclipse44Build) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(Buildship.Check.BasicIntegrationTests.IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_Eclipse45Build) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(Buildship.Check.BasicIntegrationTests.IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_Eclipse46Build) {
        }
        snapshot(Buildship.Check.BasicIntegrationTests.IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_Eclipse47IntegrationTests) {
        }
        snapshot(Buildship.Check.BasicIntegrationTests.IntegrationTests_Linux.buildTypes.IntegrationTests_Linux_Eclipse47Java9) {
            onDependencyFailure = FailureAction.IGNORE
            onDependencyCancel = FailureAction.IGNORE
        }
        snapshot(Buildship.Check.BasicIntegrationTests.IntegrationTests_Windows.buildTypes.IntegrationTests_Windows_Eclipse44Build) {
        }
        snapshot(Buildship.Check.BasicIntegrationTests.IntegrationTests_Windows.buildTypes.IntegrationTests_Windows_Eclipse47IntegrationTestsWindows) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }
})
