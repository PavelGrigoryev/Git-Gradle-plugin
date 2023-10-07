package ru.clevertec.gittaggradleplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.clevertec.gittaggradleplugin.service.impl.GitTagServiceImpl

import static ru.clevertec.gittaggradleplugin.constant.GitTagPluginConstants.GIT
import static ru.clevertec.gittaggradleplugin.constant.GitTagPluginConstants.LOGO

class GitTagPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.tasks.register('pushTag', GitTagServiceImpl) {
            group = GIT
            doFirst {
                logger.warn LOGO
            }
        }
    }

}
