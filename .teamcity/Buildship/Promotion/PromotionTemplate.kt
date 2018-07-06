package Buildship.Promotion

import jetbrains.buildServer.configs.kotlin.v2018_1.Template

object PromotionTemplate : Template({
    name = "Promotion Template"

    artifactRules = "org.eclipse.buildship.site/build/repository/** => update-site"
})