package Buildship.Check.BasicIntegrationTests.IntegrationTests_Linux.buildTypes

import Buildship.EclipseBuildTemplate
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.FailureAction

object IntegrationTests_Linux_Eclipse44Build : BuildType({
    templates(EclipseBuildTemplate)
    name = "Complete Build (Linux, Eclipse 4.4, Java 7)"
    description = "Basic integration testing of the plugin for Eclipse 4.4"

    params {
        param("eclipse.version", "44")
        param("compiler.location", "%linux.java7.oracle.64bit%/bin/javac")
        param("eclipse.test.java.home", "%linux.java7.oracle.64bit%")
    }

    dependencies {
        snapshot(Buildship.Check.Checkpoints.buildTypes.Stage1Distribution) {
            onDependencyFailure = FailureAction.CANCEL
            onDependencyCancel = FailureAction.CANCEL
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux", "RQ_319")
    }
})
