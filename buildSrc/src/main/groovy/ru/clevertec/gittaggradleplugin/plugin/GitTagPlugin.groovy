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
                    switch (branchName) {
                        case 'dev':
                            incrementMinorVersion(latestTagVersion, projectDir)
                            break
                        case 'qa':
                            incrementMinorVersion(latestTagVersion, projectDir)
                            break
                        case 'stage':
                            addRCPostfix(latestTagVersion, projectDir)
                            break
                        case 'master':
                            incrementMajorVersion(latestTagVersion, projectDir)
                            break
                        default:
                            addSnapshotPostfix(latestTagVersion, projectDir)
                            break
                    }
                }
            }
        }
    }

    private static void incrementMinorVersion(String latestTagVersion, File projectDir) {
        def result = latestTagVersion.find(/\d+\.\d+/).toDouble()
        result += 0.1
        result = "v$result"
        GitCommandBuilder.builder()
                .git()
                .tag()
                .command(result)
                .execute(projectDir)
    }

    private static void incrementMajorVersion(String latestTagVersion, File projectDir) {
        def result = latestTagVersion.find(/\d+\.\d+/).toDouble()
        if (result % 1 == 0) {
            result++
        } else {
            result = Math.ceil(result)
        }
        result = "v$result"
        GitCommandBuilder.builder()
                .git()
                .tag()
                .command(result)
                .execute(projectDir)
    }

    private static void addRCPostfix(String latestTagVersion, File projectDir) {
        def result = latestTagVersion + '-rc'
        GitCommandBuilder.builder()
                .git()
                .tag()
                .command(result)
                .execute(projectDir)
    }

    private static void addSnapshotPostfix(String latestTagVersion, File projectDir) {
        def result = latestTagVersion + '-SNAPSHOT'
        GitCommandBuilder.builder()
                .git()
                .tag()
                .command(result)
                .execute(projectDir)
    }

}
