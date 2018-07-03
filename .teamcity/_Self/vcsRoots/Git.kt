package _Self.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.vcs.GitVcsRoot

object Git : GitVcsRoot({
    name = "Buildship"
    url = "https://gradlewaregitbot@github.com/eclipse/buildship.git"
    branchSpec = "+:refs/heads/*"
    userForTags = "Gradleware Git Bot <gradlewaregitbot@gradleware.com>"
    agentGitPath = "%env.TEAMCITY_GIT_PATH%"
    agentCleanFilesPolicy = GitVcsRoot.AgentCleanFilesPolicy.NON_IGNORED_ONLY
    useMirrors = false
})
