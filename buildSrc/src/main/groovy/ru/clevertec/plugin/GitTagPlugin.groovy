package ru.clevertec.plugin

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
                def file = project.projectDir
                def tagVersion = getReferenceFromGit(file, 'git', 'describe', '--tags', '--abbrev=0')
                println tagVersion
                if (tagVersion.isEmpty()) {
                    throw new TagNotFoundException('There is no tags in the project') // todo присвоить таг
                }
                def branchName = getReferenceFromGit(file, 'git', 'branch', '--show-current')
                println branchName
                switch (branchName) {
                    case 'dev':
                        println 'dev branch'
                        break
                    case 'qa':
                        println 'qa branch'
                        break
                    default:
                        println 'default branch'
                        break
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

}
