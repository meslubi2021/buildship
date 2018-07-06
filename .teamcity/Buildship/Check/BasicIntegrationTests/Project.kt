package Buildship.Check.BasicIntegrationTests

import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("BasicIntegrationTests")
    name = "Integration Tests"
    description = "Configurations that provide full test coverage"

    subProject(Buildship.Check.BasicIntegrationTests.IntegrationTests_Linux.Project)
    subProject(Buildship.Check.BasicIntegrationTests.IntegrationTests_Windows.Project)
})
