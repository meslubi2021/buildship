package CrossVersionCoverage.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.schedule

object CrossVersionCoverage_CrossVersionCoverageLinuxEclipse47Java9 : BuildType({
    templates(_Self.buildTypes.Eclipse_Build)
    name = "Cross-version Coverage (Linux, Eclipse 4.7, Java 9)"
    description = "Basic integration testing of the plugin for Eclipse 4.7"

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
