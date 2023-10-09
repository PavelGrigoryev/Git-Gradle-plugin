package ru.clevertec.gittaggradleplugin.factory.impl

import ru.clevertec.gittaggradleplugin.factory.TagFactory
import ru.clevertec.gittaggradleplugin.model.Branch

import static ru.clevertec.gittaggradleplugin.constant.GitTagPluginConstants.*

class NoTagExistsFactory implements TagFactory {

    @Override
    String createTagName(String branchName, String latestTagVersion) {
        latestTagVersion = DEFAULT_TAG_VERSION
        switch (branchName) {
            case Branch.DEV.getName():
                latestTagVersion
                break
            case Branch.QA.getName():
                latestTagVersion
                break
            case Branch.STAGE.getName():
                "$latestTagVersion$RC"
                break
            case Branch.MASTER.getName():
                latestTagVersion
                break
            default:
                "$latestTagVersion$SNAPSHOT"
                break
        }
    }

}
