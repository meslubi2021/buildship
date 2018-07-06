package Check.Checkpoints.Eclipse_Commit

import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Check/Checkpoints/Eclipse_Commit")
    name = "Commit Builds"
    description = "Configurations for daily development"

    subProject(Check.Checkpoints.Eclipse_Commit.Commit_Linux.Project)
    subProject(Commit_Windows.Project)
})
