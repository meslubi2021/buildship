package Buildship.Check.FullTestCoverage.Linux.buildTypes

import Buildship.EclipseBuildTemplate
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.FailureAction

object Eclipse43 : BuildType({
    id("Full_Test_Coverage_Linux_Eclipse43_Java7")
    name = "Full Test Coverage (Linux, Eclipse 4.3, Java 7)"

    templates(EclipseBuildTemplate)

    params {
        param("eclipse.version", "43")
        param("compiler.location", "%linux.java7.oracle.64bit%/bin/javac")
        param("eclipse.test.java.home", "%linux.java7.oracle.64bit%")
    }

    dependencies {
        snapshot(Buildship.Check.Checkpoints.buildTypes.BasicTestCoverage) {
            onDependencyFailure = FailureAction.CANCEL
            onDependencyCancel = FailureAction.CANCEL
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux", "RQ_632")
    }
})
