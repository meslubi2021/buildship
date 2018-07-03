package Commit_Windows

import Commit_Windows.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Commit_Windows")
    name = "Commit Builds Windows"

    buildType(Commit_Windows_Eclipse42Build)
    buildType(Commit_Windows_Eclipse46Build)
    buildTypesOrder = arrayListOf(Commit_Windows_Eclipse42Build, Commit_Windows_Eclipse46Build)
})
