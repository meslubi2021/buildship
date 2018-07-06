package Buildship.Promotion

import Buildship.Promotion.buildTypes.Milestone
import Buildship.Promotion.buildTypes.Release
import Buildship.Promotion.buildTypes.Snapshot
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Promotion")
    name = "Promotion"
    description = "Promotes Buildship releases"

    template(PromotionTemplate)

    buildType(Snapshot)
    buildType(Release)
    buildType(Milestone)

    buildTypesOrder = arrayListOf(Release, Milestone, Snapshot)
})
