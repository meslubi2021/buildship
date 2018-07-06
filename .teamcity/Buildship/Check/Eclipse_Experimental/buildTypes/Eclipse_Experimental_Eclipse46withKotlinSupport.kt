package Buildship.Check.Eclipse_Experimental.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.buildSteps.gradle

object Eclipse_Experimental_Eclipse46withKotlinSupport : BuildType({
    templates(_Self.buildTypes.EclipseBuildTemplate)
    name = "Eclipse 4.7 with Kotlin Support"
    description = "Basic integration testing of the plugin for Eclipse 4.7"

    params {
        param("eclipse.version", "47")
        param("compiler.location", "%linux.java8.oracle.64bit%/bin/javac")
        param("eclipse.test.java.home", "%linux.java8.oracle.64bit%")
        param("gradle.tasks", "clean createP2Repository")
        param("env.JAVA_HOME", "%linux.java8.oracle.64bit%")
    }

    steps {
        gradle {
            name = "RUNNER_21 (1)"
            id = "RUNNER_6"
            tasks = "%gradle.tasks%"
            buildFile = ""
            gradleParams = "-Peclipse.version=%eclipse.version% -Pcompiler.location='%compiler.location%' -Pbuild.invoker=%build.invoker% -Prelease.type=%eclipse.release.type% -Peclipse.test.java.home='%eclipse.test.java.home%' --info --stacktrace -Declipse.p2.mirror=false -Dscan -Pinclude.experimental.features=true"
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
        }
        stepsOrder = arrayListOf("RUNNER_21", "RUNNER_6")
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux", "RQ_650")
        doesNotEqual("system.agent.name", "ubuntu6-agent1", "RQ_651")
    }
    
    disableSettings("RUNNER_21")
})
