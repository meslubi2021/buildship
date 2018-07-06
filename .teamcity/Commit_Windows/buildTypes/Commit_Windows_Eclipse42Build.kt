package Commit_Windows.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType

object Commit_Windows_Eclipse42Build : BuildType({
    templates(_Self.buildTypes.EclipseBuildTemplate)
    name = "Test Coverage (Windows, Eclipse 4.2, Java 7)"
    description = "Building the Eclipse plugin against Eclipse 4.2"

    artifactRules = """
        org.eclipse.buildship.core.test/build/eclipseTest/workspace/.metadata/.log => test/org.eclipse.buildship.core.test
        org.eclipse.buildship.ui.test/build/eclipseTest/workspace/.metadata/.log => test/org.eclipse.buildship.ui.test
    """.trimIndent()

    params {
        param("eclipse.version", "42")
        param("compiler.location", """%windows.java7.oracle.64bit%\bin\javac""")
        param("eclipse.test.java.home", "%windows.java7.oracle.64bit%")
        param("gradle.tasks", "clean eclipseTest")
        param("env.JAVA_HOME", "%windows.java7.oracle.64bit%")
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Windows", "RQ_334")
        doesNotMatch("teamcity.agent.name", "windows1-agent1", "RQ_335")
    }
})
