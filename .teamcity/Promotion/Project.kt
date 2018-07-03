package Promotion

import Promotion.buildTypes.*
import Promotion.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Promotion")
    name = "Promotion"
    description = "Promotion configurations"

    vcsRoot(Promotion_ToolingEclipse)

    buildType(Promotion_Snapshot_Eclipse45)
    buildType(Promotion_BuildshipMasterRelease)
    buildType(Promotion_BuildshipMasterMilestone)
    buildTypesOrder = arrayListOf(Promotion_BuildshipMasterRelease, Promotion_BuildshipMasterMilestone, Promotion_Snapshot_Eclipse45)
})
