package ru.clevertec.gittaggradleplugin.strategy.impl

import ru.clevertec.gittaggradleplugin.strategy.TagStrategy

class TagExistsStrategy implements TagStrategy {

    @Override
    String createTagName(String branchName, String latestTagVersion) {
        def tagNumber = latestTagVersion.find(/\d+\.\d+/).toDouble()
        switch (branchName) {
            case 'dev':
                incrementMinorVersion(tagNumber)
                break
            case 'qa':
                incrementMinorVersion(tagNumber)
                break
            case 'stage':
                addRCPostfix(tagNumber)
                break
            case 'master':
                incrementMajorVersion(tagNumber)
                break
            default:
                addSnapshotPostfix(tagNumber)
                break
        }
    }

    private static def incrementMinorVersion(Double tagNumber) {
        tagNumber += 0.1
        "v$tagNumber"
    }

    private static def incrementMajorVersion(Double tagNumber) {
        if (tagNumber % 1 == 0) {
            tagNumber++
        } else {
            tagNumber = Math.ceil(tagNumber)
        }
        "v$tagNumber"
    }

    private static def addRCPostfix(Double tagNumber) {
        "v$tagNumber-rc"
    }

    private static def addSnapshotPostfix(Double tagNumber) {
        "v$tagNumber-SNAPSHOT"
    }

}
