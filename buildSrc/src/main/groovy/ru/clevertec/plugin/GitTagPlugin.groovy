package ru.clevertec.plugin

import org.eclipse.jgit.api.Git
import org.gradle.api.Plugin
import org.gradle.api.Project

import static GitTagPluginConstants.LOGO

class GitTagPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.tasks.register('gitTag') {
            group = 'git'
            doFirst {
                println LOGO
            }
            doLast {
                try (Git git = Git.open(project.projectDir)) {
                    def list = git.tagList().call()
                    println list.toString()
                }
            }
        }
    }

}
