package BasicIntegrationTests

import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("BasicIntegrationTests")
    name = "Integration Tests"
    description = "Configurations that provide full test coverage"

    subProject(IntegrationTests_Linux.Project)
    subProject(IntegrationTests_Windows.Project)
})
