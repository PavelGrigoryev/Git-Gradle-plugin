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
                            def tag = incrementMinorVersion(tagNumber)
                            addTagToLocalAndRemote(tag, projectDir)
                            break
                        case 'qa':
                            def tag = incrementMinorVersion(tagNumber)
                            addTagToLocalAndRemote(tag, projectDir)
                            break
                        case 'stage':
                            def tag = addRCPostfix(tagNumber)
                            addTagToLocalAndRemote(tag, projectDir)
                            break
                        case 'master':
                            def tag = incrementMajorVersion(tagNumber)
                            addTagToLocalAndRemote(tag, projectDir)
                            break
                        default:
                            def tag = addSnapshotPostfix(tagNumber)
                            addTagToLocalAndRemote(tag, projectDir)
                            break
                    }
                }
            }
        }
    }

    private static def incrementMinorVersion(Double tagNumber) {
        tagNumber += 0.1
        "v$tagNumber"
    }

    private static def incrementMajorVersion(Double tagNumber) {
        if (tagNumber % 1 == 0) {
            tagNumber++
        } else {
            tagNumber = Math.ceil(tagNumber)
        }
        "v$tagNumber"
    }

    private static def addRCPostfix(Double tagNumber) {
        "v$tagNumber-rc"
    }

    private static def addSnapshotPostfix(Double tagNumber) {
        "v$tagNumber-SNAPSHOT"
    }

    private static void addTagToLocalAndRemote(String tag, File projectDir) {
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

}
