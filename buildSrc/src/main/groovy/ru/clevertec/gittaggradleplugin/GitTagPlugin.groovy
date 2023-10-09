package ru.clevertec.gittaggradleplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.clevertec.gittaggradleplugin.service.GitTagService
import ru.clevertec.gittaggradleplugin.service.PushTagExtension

import static ru.clevertec.gittaggradleplugin.constant.GitTagPluginConstants.*

class GitTagPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create(PUSH_TAG, PushTagExtension)
        project.tasks.register(PUSH_TAG, GitTagService) {
            group = GIT
            doFirst {
                logger.warn LOGO
            }
        }
    }

}
