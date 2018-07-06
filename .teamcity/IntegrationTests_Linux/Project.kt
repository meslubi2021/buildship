package IntegrationTests_Linux

import IntegrationTests_Linux.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("IntegrationTests_Linux")
    name = "Integration Tests Linux"

    buildType(IntegrationTests_Linux_Eclipse42Build)
    buildType(IntegrationTests_Linux_Eclipse44Build)
    buildType(IntegrationTests_Linux_Eclipse47IntegrationTests)
    buildType(IntegrationTests_Linux_CompleteBuildLinuxEclipse48Java8)
    buildType(IntegrationTests_Linux_Eclipse45Build)
    buildType(IntegrationTests_Linux_Eclipse46Build)
    buildType(IntegrationTests_Linux_Eclipse43Build)
    buildType(IntegrationTests_Linux_Eclipse47Java9)
})
