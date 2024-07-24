package Buildship.Check30.BasicTestCoverage.Windows.buildTypes

import Buildship.EclipseBuildTemplate
import jetbrains.buildServer.configs.kotlin.v2018_2.BuildType

object Eclipse43 : BuildType({
    id("Basic_Test_Coverage_Windows_Eclipse43_java8_30")
    name = "Basic Test Coverage (Windows, Eclipse 4.3, Java 8)"

    templates(EclipseBuildTemplate)

    params {
        param("eclipse.version", "43")
        param("compiler.location", """%windows.java8.oracle.64bit%\bin\javac""")
        param("eclipse.test.java.home", "%windows.java8.oracle.64bit%")
        param("gradle.tasks", "clean eclipseTest")
        param("env.JAVA_HOME", "%windows.java8.oracle.64bit%")
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Windows", "RQ_334")
    }
})
