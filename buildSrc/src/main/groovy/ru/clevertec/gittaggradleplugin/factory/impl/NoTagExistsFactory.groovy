package ru.clevertec.gittaggradleplugin.factory.impl

import ru.clevertec.gittaggradleplugin.factory.TagFactory

class NoTagExistsFactory implements TagFactory {

    @Override
    String createTagName(String branchName, String latestTagVersion) {
        latestTagVersion = 'v0.1'
        switch (branchName) {
            case 'dev':
                latestTagVersion
                break
            case 'qa':
                latestTagVersion
                break
            case 'stage':
                "$latestTagVersion-rc"
                break
            case 'master':
                latestTagVersion
                break
            default:
                "$latestTagVersion-SNAPSHOT"
                break
        }
    }

}
