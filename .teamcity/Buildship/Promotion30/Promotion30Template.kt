package Buildship.Promotion30

import Buildship.Check30.Checkpoints.buildTypes.Final
import Buildship.GitHubVcsRoot
import jetbrains.buildServer.configs.kotlin.v2018_1.CheckoutMode
import jetbrains.buildServer.configs.kotlin.v2018_1.FailureAction
import jetbrains.buildServer.configs.kotlin.v2018_1.Template

object Promotion30Template : Template({
    name = "Promotion30 Template"

    artifactRules = "org.eclipse.buildship.site/build/repository/** => update-site"

    // The artifact upload requires uses ssh which requires manual confirmation. to work around that, we use the same
    // machine for the upload.
    // TODO We should separate the update site generation and the artifact upload into two separate steps.
    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux")
        matches("teamcity.agent.name", "dev3.*")
    }

    vcs {
        root(GitHubVcsRoot)

        checkoutMode = CheckoutMode.ON_AGENT
        cleanCheckout = true
        showDependenciesChanges = true
    }

    failureConditions {
        errorMessage = true
    }

    dependencies {
        snapshot(Final) {
            onDependencyFailure = FailureAction.FAIL_TO_START
            onDependencyCancel = FailureAction.CANCEL
        }
    }
})