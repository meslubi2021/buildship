package Buildship.Check.CrossVersionCoverage.buildTypes

import Buildship.EclipseBuildTemplate
import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.schedule

object Eclipse47Java9 : BuildType({
    id("Cross_Version_Coverage_Linux_Eclipse47_java9")
    name = "Cross-Version Coverage (Linux, Eclipse 4.7, Java 9)"

    templates(EclipseBuildTemplate)

    params {
        param("eclipse.version", "47")
        param("compiler.location", "%linux.java8.oracle.64bit%/bin/javac")
        param("eclipse.test.java.home", "%linux.java9.oracle.64bit%")
        param("gradle.tasks", "clean crossVersionEclipseTest")
        param("env.JAVA_HOME", "%linux.java9.oracle.64bit%")
    }

    triggers {
        schedule {
            id = "TRIGGER_62"
            schedulingPolicy = daily {
                hour = 4
                timezone = "Europe/Budapest"
            }
            branchFilter = """
                +:donat/cleanup/add-test-coverage
                +:release-3.0
            """.trimIndent()
            triggerBuild = always()
            param("revisionRule", "lastFinished")
            param("dayOfWeek", "Sunday")
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux", "RQ_650")
        doesNotEqual("system.agent.name", "ubuntu6-agent1", "RQ_651")
    }
})
