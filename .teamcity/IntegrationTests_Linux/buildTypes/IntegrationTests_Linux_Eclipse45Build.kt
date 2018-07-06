package IntegrationTests_Linux.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.FailureAction

object IntegrationTests_Linux_Eclipse45Build : BuildType({
    templates(_Self.buildTypes.Eclipse_Build)
    name = "Complete Build (Linux, Eclipse 4.5, Java 7)"
    description = "Basic integration testing of the plugin for Eclipse 4.5"

    params {
        param("eclipse.version", "45")
        param("compiler.location", "%linux.java7.oracle.64bit%/bin/javac")
        param("eclipse.test.java.home", "%linux.java7.oracle.64bit%")
    }

    dependencies {
        snapshot(Check.Checkpoints.buildTypes.Checkpoints_Stage1Distribution) {
            onDependencyFailure = FailureAction.CANCEL
            onDependencyCancel = FailureAction.CANCEL
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux", "RQ_301")
        doesNotEqual("system.agent.name", "ubuntu6-agent1", "RQ_302")
    }
})
