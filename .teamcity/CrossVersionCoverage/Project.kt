package CrossVersionCoverage

import CrossVersionCoverage.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("CrossVersionCoverage")
    name = "Cross-version Integration Tests"
    description = "Runs tests with all supported Gradle versions"

    buildType(CrossVersionCoverage_CrossVersionCoverageLinuxEclipse47Java9)
})
