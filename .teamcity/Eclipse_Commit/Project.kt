package Eclipse_Commit

import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Eclipse_Commit")
    name = "Commit Builds"
    description = "Configurations for daily development"

    subProject(Commit_Linux.Project)
    subProject(Commit_Windows.Project)
})
