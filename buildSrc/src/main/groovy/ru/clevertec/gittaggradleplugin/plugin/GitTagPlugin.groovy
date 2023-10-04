package ru.clevertec.gittaggradleplugin.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.clevertec.gittaggradleplugin.builder.GitCommandBuilder
import ru.clevertec.gittaggradleplugin.exception.AlreadyTaggedException
import ru.clevertec.gittaggradleplugin.strategy.impl.NoTagExistsStrategy
import ru.clevertec.gittaggradleplugin.strategy.impl.TagExistsStrategy

import static ru.clevertec.gittaggradleplugin.constant.GitTagPluginConstants.GIT
import static ru.clevertec.gittaggradleplugin.constant.GitTagPluginConstants.LOGO

class GitTagPlugin implements Plugin<Project> {

    def noTagExistsStrategy = new NoTagExistsStrategy()
    def tagExistsStrategy = new TagExistsStrategy()

    @Override
    void apply(Project project) {
        project.tasks.register('gitTag') {
            group = GIT
            doFirst {
                println LOGO
            }
            doLast {
                def projectDir = project.projectDir
                def latestTagVersion = GitCommandBuilder.builder()
                        .git()
                        .describe()
                        .tags()
                        .abbrev(0)
                        .execute(projectDir)
                def branchName = GitCommandBuilder.builder()
                        .git()
                        .branch()
                        .showCurrent()
                        .execute(projectDir)
                if (latestTagVersion.isEmpty()) {
                    def tagName = noTagExistsStrategy.createTagNameByBranchName(branchName, latestTagVersion)
                    noTagExistsStrategy.saveTagToLocalAndRemote(tagName, projectDir)
                    return
                }
                def currentTagVersion = GitCommandBuilder.builder()
                        .git()
                        .describe()
                        .tags()
                        .execute(projectDir)
                if (latestTagVersion == currentTagVersion) {
                    throw new AlreadyTaggedException("The current state of the project is already tagged $currentTagVersion by git")
                } else {
                    def tagName = tagExistsStrategy.createTagNameByBranchName(branchName, latestTagVersion)
                    tagExistsStrategy.saveTagToLocalAndRemote(tagName, projectDir)
                }
            }
        }
    }

}
