package Commit_Windows.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType

object Commit_Windows_Eclipse46Build : BuildType({
    templates(_Self.buildTypes.Eclipse_Build)
    name = "Test Coverage (Windows, Eclipse 4.6, Java 8)"
    description = "Building the Eclipse plugin against Eclipse 4.6 without running the tests"

    artifactRules = """
        org.eclipse.buildship.core.test/build/eclipseTest/workspace/.metadata/.log => test/org.eclipse.buildship.core.test
        org.eclipse.buildship.ui.test/build/eclipseTest/workspace/.metadata/.log => test/org.eclipse.buildship.ui.test
    """.trimIndent()

    params {
        param("eclipse.version", "46")
        param("compiler.location", """%windows.java8.oracle.64bit%\bin\javac""")
        param("eclipse.test.java.home", "%windows.java8.oracle.64bit%")
        param("gradle.tasks", "clean eclipseTest")
        param("env.JAVA_HOME", "%windows.java8.oracle.64bit%")
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Windows", "RQ_501")
        doesNotMatch("teamcity.agent.name", "windows1-agent1", "RQ_502")
    }
})
