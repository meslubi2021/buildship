package Buildship.Check.BasicTestCoverage.Linux.buildTypes

import Buildship.EclipseBuildTemplate
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType

object Eclipse42 : BuildType({
    id("Basic_Test_Coverage_Linux_Eclipse42_java7")
    name = "Basic Test Coverage (Linux, Eclipse 4.2, Java 7)"

    templates(EclipseBuildTemplate)

    artifactRules = """
        org.eclipse.buildship.site/build/repository/** => update-site
        org.eclipse.buildship.core.test/build/eclipseTest/workspace/.metadata/.log => test/org.eclipse.buildship.core.test
        org.eclipse.buildship.ui.test/build/eclipseTest/workspace/.metadata/.log => test/org.eclipse.buildship.ui.test
        org.eclipse.buildship.stsmigration.test/build/screenshots => test/screenshots
    """.trimIndent()

    params {
        param("eclipse.version", "42")
        param("compiler.location", "%linux.java7.oracle.64bit%/bin/javac")
        param("eclipse.test.java.home", "%linux.java7.oracle.64bit%")
        param("gradle.tasks", "clean eclipseTest")
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux", "RQ_171")
    }
})
