package CrossVersionCoverage

import CrossVersionCoverage.buildTypes.CrossVersionCoverage_CrossVersionCoverageLinuxEclipse47Java9
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("CrossVersionCoverage")
    name = "Cross-version Integration Tests"
    description = "Runs tests with all supported Gradle versions"

    buildType(CrossVersionCoverage_CrossVersionCoverageLinuxEclipse47Java9)
})
