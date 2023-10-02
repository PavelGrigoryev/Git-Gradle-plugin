package ru.clevertec.plugin

import org.eclipse.jgit.api.Git
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class GitPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def map = [group: 'git', type: DefaultTask]
        project.task(map, 'gitTag') {
            doLast {
                Git git = Git.open(project.projectDir)
                def list = git.tagList().call()
                println list.toString()
            }
        }
    }

}
