package Buildship.Check30.FullTestCoverage.Linux.buildTypes

import Buildship.Check30.Checkpoints.buildTypes.BasicTestCoverage
import Buildship.EclipseBuildTemplate
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.FailureAction

object Eclipse44 : BuildType({
    id("Full_Test_Coverage_Linux_Eclipse44_Java7_30")
    name = "Full Test Coverage (Linux, Eclipse 4.4, Java 7)"

    templates(EclipseBuildTemplate)

    params {
        param("eclipse.version", "44")
        param("compiler.location", "%linux.java7.oracle.64bit%/bin/javac")
        param("eclipse.test.java.home", "%linux.java7.oracle.64bit%")
    }

    dependencies {
        snapshot(BasicTestCoverage) {
            onDependencyFailure = FailureAction.CANCEL
            onDependencyCancel = FailureAction.CANCEL
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux", "RQ_319")
    }
})
