package Buildship.Check30.BasicTestCoverage.Linux.buildTypes

import Buildship.EclipseBuildTemplate
import jetbrains.buildServer.configs.kotlin.v2018_2.BuildType

object Eclipse411 : BuildType({
    id("Basic_Test_Coverage_Linux_Eclipse411_Java8_30")
    name = "Basic Test Coverage (Linux, Eclipse 2019-03, Java 8)"

    templates(EclipseBuildTemplate)

    params {
        param("eclipse.version", "411")
        param("compiler.location", "%linux.java8.oracle.64bit%/bin/javac")
        param("eclipse.test.java.home", "%linux.java8.oracle.64bit%")
        param("gradle.tasks", "clean eclipseTest")
        param("env.JAVA_HOME", "%linux.java8.oracle.64bit%")
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux", "RQ_650")
    }
})
