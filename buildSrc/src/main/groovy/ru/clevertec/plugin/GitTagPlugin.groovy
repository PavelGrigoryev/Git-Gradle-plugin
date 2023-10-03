package ru.clevertec.plugin

import org.eclipse.jgit.api.Git
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project

import static GitTagPluginConstants.LOGO

class GitTagPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def map = [group: 'git', type: DefaultTask]
        project.task(map, 'gitTag') {
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
