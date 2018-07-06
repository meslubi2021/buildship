package Buildship.Check.Experimental

import Buildship.Check.Experimental.buildTypes.Eclipse47KotlinSupport
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Experimental")
    name = "Experimental"

    buildType(Eclipse47KotlinSupport)
})
