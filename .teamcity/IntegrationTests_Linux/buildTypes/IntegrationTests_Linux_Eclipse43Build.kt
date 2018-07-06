package IntegrationTests_Linux.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_1.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_1.FailureAction

object IntegrationTests_Linux_Eclipse43Build : BuildType({
    templates(_Self.buildTypes.EclipseBuildTemplate)
    name = "Complete Build (Linux, Eclipse 4.3, Java 7)"
    description = "Basic integration testing of the plugin for Eclipse 4.3"

    params {
        param("eclipse.version", "43")
        param("compiler.location", "%linux.java7.oracle.64bit%/bin/javac")
        param("eclipse.test.java.home", "%linux.java7.oracle.64bit%")
    }

    dependencies {
        snapshot(Check.Checkpoints.buildTypes.Stage1Distribution) {
            onDependencyFailure = FailureAction.CANCEL
            onDependencyCancel = FailureAction.CANCEL
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux", "RQ_632")
    }
})
