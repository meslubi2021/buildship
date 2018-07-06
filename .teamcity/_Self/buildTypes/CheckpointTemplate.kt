package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_1.CheckoutMode
import jetbrains.buildServer.configs.kotlin.v2018_1.Template

object CheckpointTemplate : Template({
    vcs {
        root(_Self.vcsRoots.GitHubEclipseBuildship)
        checkoutMode = CheckoutMode.ON_AGENT
    }
})
