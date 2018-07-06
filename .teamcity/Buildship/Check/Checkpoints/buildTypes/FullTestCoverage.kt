package Buildship.Check.Checkpoints.buildTypes

import Buildship.GitHubVcsRoot
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.CheckoutMode
import jetbrains.buildServer.configs.kotlin.v2018_1.FailureAction
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.finishBuildTrigger

object FullTestCoverage : BuildType({
    id("Checkpoint_Full_Test_Coverage")
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
            branchFilter = "+:*"
        }
    }

    dependencies {
        snapshot(BasicTestCoverage) {
            onDependencyFailure = FailureAction.CANCEL
            onDependencyCancel = FailureAction.CANCEL
        }
        snapshot(Buildship.Check.FullTestCoverage.Linux.buildTypes.Eclipse48) {
        }
        snapshot(Buildship.Check.FullTestCoverage.Linux.buildTypes.Eclipse42) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(Buildship.Check.FullTestCoverage.Linux.buildTypes.Eclipse43) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(Buildship.Check.FullTestCoverage.Linux.buildTypes.Eclipse44) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(Buildship.Check.FullTestCoverage.Linux.buildTypes.Eclipse45) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(Buildship.Check.FullTestCoverage.Linux.buildTypes.Eclipse46) {
        }
        snapshot(Buildship.Check.FullTestCoverage.Linux.buildTypes.Eclipse47) {
        }
        snapshot(Buildship.Check.FullTestCoverage.Linux.buildTypes.Eclipse47Java9) {
            onDependencyFailure = FailureAction.IGNORE
            onDependencyCancel = FailureAction.IGNORE
        }
        snapshot(Buildship.Check.FullTestCoverage.Windows.buildTypes.Eclipse44) {
        }
        snapshot(Buildship.Check.FullTestCoverage.Windows.buildTypes.Eclipse47) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }
})
