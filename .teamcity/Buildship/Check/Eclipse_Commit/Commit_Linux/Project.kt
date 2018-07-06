package Buildship.Check.Eclipse_Commit.Commit_Linux

import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Commit_Linux")
    name = "Commit Builds Linux"

    buildType(Buildship.Check.Eclipse_Commit.Commit_Linux.buildTypes.Eclipse42)
    buildType(Buildship.Check.Eclipse_Commit.Commit_Linux.buildTypes.Eclipse47)
})
