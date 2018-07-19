package Buildship.Check.BasicTestCoverage.Windows.buildTypes

import Buildship.EclipseBuildTemplate
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType

object Eclipse42 : BuildType({
    id("Basic_Test_Coverage_Windows_Eclipse42_java7")
    name = "Basic Test Coverage (Windows, Eclipse 4.2, Java 7)"

    templates(EclipseBuildTemplate)

    params {
        param("eclipse.version", "42")
        param("compiler.location", """%windows.java7.oracle.64bit%\bin\javac""")
        param("eclipse.test.java.home", "%windows.java7.oracle.64bit%")
        param("gradle.tasks", "clean eclipseTest")
        param("env.JAVA_HOME", "%windows.java7.oracle.64bit%")
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Windows", "RQ_334")
    }
})
