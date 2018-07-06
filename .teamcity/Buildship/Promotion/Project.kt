package Buildship.Promotion

import Buildship.Promotion.buildTypes.Promotion_BuildshipMasterMilestone
import Buildship.Promotion.buildTypes.Promotion_BuildshipMasterRelease
import Buildship.Promotion.buildTypes.Promotion_Snapshot_Eclipse45
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Promotion")
    name = "Promotion"
    description = "Promotion configurations"

    buildType(Promotion_Snapshot_Eclipse45)
    buildType(Promotion_BuildshipMasterRelease)
    buildType(Promotion_BuildshipMasterMilestone)

    buildTypesOrder = arrayListOf(Promotion_BuildshipMasterRelease, Promotion_BuildshipMasterMilestone, Promotion_Snapshot_Eclipse45)
})
