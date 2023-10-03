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
                def latestTagVersion = getReferenceFromGit(file, GitCommandBuilder.builder()
                        .git()
                        .describe()
                        .tags()
                        .abbrev(0)
                        .build())
                println latestTagVersion
                if (latestTagVersion.isEmpty()) {
                    throw new TagNotFoundException('There is no tags in the project') // todo присвоить таг
                }
                def currentTagVersion = getReferenceFromGit(file, GitCommandBuilder.builder()
                        .git()
                        .describe()
                        .tags()
                        .build())
                println currentTagVersion

                if (latestTagVersion == currentTagVersion) {
                    throw new AlreadyTaggedException("The current state of the project is already tagged $currentTagVersion by git")
                } else {
                    def branchName = getReferenceFromGit(file, GitCommandBuilder.builder()
                            .git()
                            .branch()
                            .showCurrent()
                            .build())
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

    private static def getReferenceFromGit(File file, String... commands) {
        def process = new ProcessBuilder(commands)
                .directory(file)
                .start()
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            def temp = ''
            def line = new StringBuilder()
            while ((temp = reader.readLine()) != null) {
                line.append(temp)
            }
            line.toString()
        }
    }

    private static void incrementMinorVersion(String latestTagVersion, File file) {
        def result = latestTagVersion.find(/\d+\.\d+/).toDouble()
        result += 0.1
        result = 'v' + result
        getReferenceFromGit(file, GitCommandBuilder.builder()
                .git()
                .tag()
                .command(result)
                .build())
    }

    private static void incrementMajorVersion(String latestTagVersion, File file) {
        def result = latestTagVersion.find(/\d+\.\d+/).toDouble()
        result = Math.ceil(result)
        result = 'v' + result
        getReferenceFromGit(file, GitCommandBuilder.builder()
                .git()
                .tag()
                .command(result)
                .build())
    }

    private static void addRCPostFix(String latestTagVersion, File file) {
        def result = latestTagVersion + '-rc'
        getReferenceFromGit(file, GitCommandBuilder.builder()
                .git()
                .tag()
                .command(result)
                .build())
    }

}
