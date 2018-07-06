package Buildship.Check.Eclipse_Commit

import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Eclipse_Commit")
    name = "Commit Builds"
    description = "Configurations for daily development"

    subProject(Buildship.Check.Eclipse_Commit.Commit_Linux.Project)
    subProject(Buildship.Check.Eclipse_Commit.Commit_Windows.Project)
})
