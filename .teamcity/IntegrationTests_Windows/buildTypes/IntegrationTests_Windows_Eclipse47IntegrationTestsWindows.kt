package IntegrationTests_Windows.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType

object IntegrationTests_Windows_Eclipse47IntegrationTestsWindows : BuildType({
    templates(_Self.buildTypes.EclipseBuildTemplate)
    name = "Complete Build (Windows, Eclipse 4.7, Java 8)"
    description = "Basic integration testing of the plugin for Eclipse 4.7"

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
