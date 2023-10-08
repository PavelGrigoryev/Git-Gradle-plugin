package ru.clevertec.gittaggradleplugin.factory.impl

import ru.clevertec.gittaggradleplugin.factory.Branch
import ru.clevertec.gittaggradleplugin.factory.TagFactory

class NoTagExistsFactory implements TagFactory {

    @Override
    String createTagName(String branchName, String latestTagVersion) {
        latestTagVersion = 'v0.1'
        switch (branchName) {
            case Branch.DEV.getName():
                latestTagVersion
                break
            case Branch.QA.getName():
                latestTagVersion
                break
            case Branch.STAGE.getName():
                "$latestTagVersion-rc"
                break
            case Branch.MASTER.getName():
                latestTagVersion
                break
            default:
                "$latestTagVersion-SNAPSHOT"
                break
        }
    }

}
