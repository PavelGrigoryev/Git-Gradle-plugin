package ru.clevertec.gittaggradleplugin.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.clevertec.gittaggradleplugin.builder.GitCommandBuilder
import ru.clevertec.gittaggradleplugin.exception.AlreadyTaggedException
import ru.clevertec.gittaggradleplugin.exception.TagNotFoundException

import static ru.clevertec.gittaggradleplugin.constant.GitTagPluginConstants.GIT
import static ru.clevertec.gittaggradleplugin.constant.GitTagPluginConstants.LOGO

class GitTagPlugin implements Plugin<Project> {

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
                println latestTagVersion
                if (latestTagVersion.isEmpty()) {
                    throw new TagNotFoundException('There is no tags in the project') // todo присвоить таг
                }
                def currentTagVersion = GitCommandBuilder.builder()
                        .git()
                        .describe()
                        .tags()
                        .execute(projectDir)
                println currentTagVersion

                if (latestTagVersion == currentTagVersion) {
                    throw new AlreadyTaggedException("The current state of the project is already tagged $currentTagVersion by git")
                } else {
                    def branchName = GitCommandBuilder.builder()
                            .git()
                            .branch()
                            .showCurrent()
                            .execute(projectDir)
                    println branchName
                    def tagNumber = latestTagVersion.find(/\d+\.\d+/).toDouble()
                    switch (branchName) {
                        case 'dev':
                            incrementMinorVersion(tagNumber, projectDir)
                            break
                        case 'qa':
                            incrementMinorVersion(tagNumber, projectDir)
                            break
                        case 'stage':
                            addRCPostfix(tagNumber, projectDir)
                            break
                        case 'master':
                            incrementMajorVersion(tagNumber, projectDir)
                            break
                        default:
                            addSnapshotPostfix(tagNumber, projectDir)
                            break
                    }
                }
            }
        }
    }

    private static void incrementMinorVersion(Double tagNumber, File projectDir) {
        tagNumber += 0.1
        def tag = "v$tagNumber"
        GitCommandBuilder.builder()
                .git()
                .tag()
                .command(tag)
                .execute(projectDir)
    }

    private static void incrementMajorVersion(Double tagNumber, File projectDir) {
        if (tagNumber % 1 == 0) {
            tagNumber++
        } else {
            tagNumber = Math.ceil(tagNumber)
        }
        def tag = "v$tagNumber"
        GitCommandBuilder.builder()
                .git()
                .tag()
                .command(tag)
                .execute(projectDir)
    }

    private static void addRCPostfix(Double tagNumber, File projectDir) {
        def tag = "v$tagNumber-rc"
        GitCommandBuilder.builder()
                .git()
                .tag()
                .command(tag)
                .execute(projectDir)
        GitCommandBuilder.builder()
                .git()
                .push()
                .origin()
                .command(tag)
                .execute(projectDir)
    }

    private static void addSnapshotPostfix(Double tagNumber, File projectDir) {
        def tag = "v$tagNumber-SNAPSHOT"
        GitCommandBuilder.builder()
                .git()
                .tag()
                .command(tag)
                .execute(projectDir)
    }

}
