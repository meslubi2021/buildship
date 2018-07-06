package Buildship.Check.BasicIntegrationTests.IntegrationTests_Linux.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.FailureAction

object IntegrationTests_Linux_CompleteBuildLinuxEclipse48Java8 : BuildType({
    templates(_Self.buildTypes.EclipseBuildTemplate)
    name = "Complete Build (Linux, Eclipse 4.8, Java 8)"
    description = "Basic integration testing of the plugin for Eclipse 4.7"

    params {
        param("eclipse.version", "48")
        param("compiler.location", "%linux.java8.oracle.64bit%/bin/javac")
        param("eclipse.test.java.home", "%linux.java8.oracle.64bit%")
        param("env.JAVA_HOME", "%linux.java8.oracle.64bit%")
    }

    dependencies {
        snapshot(Buildship.Check.Checkpoints.buildTypes.Stage1Distribution) {
            onDependencyFailure = FailureAction.CANCEL
            onDependencyCancel = FailureAction.CANCEL
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux", "RQ_650")
        doesNotEqual("system.agent.name", "ubuntu6-agent1", "RQ_651")
    }
})
