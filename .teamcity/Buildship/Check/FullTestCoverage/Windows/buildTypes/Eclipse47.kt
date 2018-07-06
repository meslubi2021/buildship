package Buildship.Check.FullTestCoverage.Windows.buildTypes

import Buildship.EclipseBuildTemplate
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType

object Eclipse47 : BuildType({
    id("Full_Test_Coverage_Windows_Eclipse427_Java8")
    name = "Full Test Coverage (Windows, Eclipse 4.7, Java 8)"

    templates(EclipseBuildTemplate)

    params {
        param("eclipse.version", "47")
        param("compiler.location", """%windows.java8.oracle.64bit%\bin\javac""")
        param("eclipse.test.java.home", "%windows.java8.oracle.64bit%")
        param("env.JAVA_HOME", "%windows.java8.oracle.64bit%")
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Windows", "RQ_429")
        doesNotMatch("system.agent.name", "windows1-agent1", "RQ_430")
    }
})
