package Buildship.Check.Eclipse_Commit.Commit_Windows

import Buildship.Check.Eclipse_Commit.Commit_Windows.buildTypes.Commit_Windows_Eclipse42Build
import Buildship.Check.Eclipse_Commit.Commit_Windows.buildTypes.Commit_Windows_Eclipse46Build
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Commit_Windows")
    name = "Commit Builds Windows"

    buildType(Commit_Windows_Eclipse42Build)
    buildType(Commit_Windows_Eclipse46Build)
    buildTypesOrder = arrayListOf(Commit_Windows_Eclipse42Build, Commit_Windows_Eclipse46Build)
})
