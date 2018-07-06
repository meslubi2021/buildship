package Buildship.Check.Eclipse_Commit.Commit_Linux.buildTypes

import Buildship.EclipseBuildTemplate
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType

object Eclipse47 : BuildType({
    templates(EclipseBuildTemplate)
    name = "Basic Test Coverage (Linux, Eclipse 4.7, Java 8)"

    artifactRules = """
        org.eclipse.buildship.core.test/build/eclipseTest/workspace/.metadata/.log => test/org.eclipse.buildship.core.test
        org.eclipse.buildship.ui.test/build/eclipseTest/workspace/.metadata/.log => test/org.eclipse.buildship.ui.test
    """.trimIndent()

    params {
        param("eclipse.version", "47")
        param("compiler.location", "%linux.java8.oracle.64bit%/bin/javac")
        param("eclipse.test.java.home", "%linux.java8.oracle.64bit%")
        param("gradle.tasks", "clean eclipseTest")
        param("env.JAVA_HOME", "%linux.java8.oracle.64bit%")
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux", "RQ_643")
    }
})
