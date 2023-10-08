package ru.clevertec.gittaggradleplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.clevertec.gittaggradleplugin.service.GitTagService
import ru.clevertec.gittaggradleplugin.service.PushTagExtension

import static ru.clevertec.gittaggradleplugin.constant.GitTagPluginConstants.GIT
import static ru.clevertec.gittaggradleplugin.constant.GitTagPluginConstants.LOGO

class GitTagPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create('pushTag', PushTagExtension)
        project.tasks.register('pushTag', GitTagService) {
            group = GIT
            doFirst {
                logger.warn LOGO
            }
        }
    }

}
