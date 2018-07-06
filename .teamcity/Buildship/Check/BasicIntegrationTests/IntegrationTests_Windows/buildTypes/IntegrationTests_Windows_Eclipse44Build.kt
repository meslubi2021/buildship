package Buildship.Check.BasicIntegrationTests.IntegrationTests_Windows.buildTypes

import Buildship.EclipseBuildTemplate
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.retryBuild

object IntegrationTests_Windows_Eclipse44Build : BuildType({
    templates(EclipseBuildTemplate)
    name = "Complete Build (Windows, Eclipse 4.2, Java 7)"
    description = "Basic integration testing of the plugin for Eclipse 4.2"

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
