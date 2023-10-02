package ru.clevertec.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.impldep.org.eclipse.jgit.api.Git

class GitPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def map = [group: 'git', type: DefaultTask]
        project.task(map, 'gitTag') {
            doLast {
                println "Hello World $project.projectDir"
                Git git = Git.open(new File('https://github.com/PavelGrigoryev/Clever-Bank-Reactive-Remaster'))
                def list = git.tagList().call()
                println list.toString()
            }
        }
    }

}
