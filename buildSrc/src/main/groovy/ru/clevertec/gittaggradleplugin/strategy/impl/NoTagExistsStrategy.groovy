package ru.clevertec.gittaggradleplugin.strategy.impl

import ru.clevertec.gittaggradleplugin.strategy.TagStrategy

class NoTagExistsStrategy implements TagStrategy {

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
