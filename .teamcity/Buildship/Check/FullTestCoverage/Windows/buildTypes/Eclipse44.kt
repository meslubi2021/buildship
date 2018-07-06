package Buildship.Check.FullTestCoverage.Windows.buildTypes

import Buildship.EclipseBuildTemplate
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.retryBuild

object Eclipse44 : BuildType({
    id("Full_Test_Coverage_Windows_Eclipse42_Java7")
    name = "Full Test Coverage (Windows, Eclipse 4.2, Java 7)"

    templates(EclipseBuildTemplate)

    params {
        param("eclipse.version", "42")
        param("compiler.location", """%windows.java7.oracle.64bit%\bin\javac""")
        param("eclipse.test.java.home", "%windows.java7.oracle.64bit%")
        param("env.JAVA_HOME", "%windows.java7.oracle.64bit%")
    }

    triggers {
        retryBuild {
            id = "retryBuildTrigger"
            attempts = 2
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Windows", "RQ_489")
        doesNotMatch("system.agent.name", "windows1-agent1", "RQ_490")
    }
})
