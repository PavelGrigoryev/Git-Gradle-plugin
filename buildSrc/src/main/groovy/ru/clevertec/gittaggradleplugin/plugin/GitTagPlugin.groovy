package ru.clevertec.gittaggradleplugin.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.clevertec.gittaggradleplugin.builder.GitCommandBuilder
import ru.clevertec.gittaggradleplugin.exception.AlreadyTaggedException
import ru.clevertec.gittaggradleplugin.exception.TagNotFoundException

import static ru.clevertec.gittaggradleplugin.constant.GitTagPluginConstants.LOGO

class GitTagPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.tasks.register('gitTag') {
            group = 'git'
            doFirst {
                println LOGO
            }
            doLast {
                def file = project.projectDir
                def latestTagVersion = GitCommandBuilder.builder()
                        .git()
                        .describe()
                        .tags()
                        .abbrev(0)
                        .execute(file)
                println latestTagVersion
                if (latestTagVersion.isEmpty()) {
                    throw new TagNotFoundException('There is no tags in the project') // todo присвоить таг
                }
                def currentTagVersion = GitCommandBuilder.builder()
                        .git()
                        .describe()
                        .tags()
                        .execute(file)
                println currentTagVersion

                if (latestTagVersion == currentTagVersion) {
                    throw new AlreadyTaggedException("The current state of the project is already tagged $currentTagVersion by git")
                } else {
                    def branchName = GitCommandBuilder.builder()
                            .git()
                            .branch()
                            .showCurrent()
                            .execute(file)
                    println branchName
                    switch (branchName) {
                        case 'dev':
                            incrementMinorVersion(latestTagVersion, file)
                            break
                        case 'qa':
                            incrementMinorVersion(latestTagVersion, file)
                            break
                        case 'stage':
                            addRCPostFix(latestTagVersion, file)
                            break
                        case 'master':
                            incrementMajorVersion(latestTagVersion, file)
                            break
                        default:
                            println 'default branch'
                            break
                    }
                }
            }
        }
    }

    private static void incrementMinorVersion(String latestTagVersion, File file) {
        def result = latestTagVersion.find(/\d+\.\d+/).toDouble()
        result += 0.1
        result = 'v' + result
        GitCommandBuilder.builder()
                .git()
                .tag()
                .command(result)
                .execute(file)
    }

    private static void incrementMajorVersion(String latestTagVersion, File file) {
        def result = latestTagVersion.find(/\d+\.\d+/).toDouble()
        result = Math.ceil(result)
        result = 'v' + result
        GitCommandBuilder.builder()
                .git()
                .tag()
                .command(result)
                .execute(file)
    }

    private static void addRCPostFix(String latestTagVersion, File file) {
        def result = latestTagVersion + '-rc'
        GitCommandBuilder.builder()
                .git()
                .tag()
                .command(result)
                .execute(file)
    }

}
