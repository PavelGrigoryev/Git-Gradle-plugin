package ru.clevertec.gittaggradleplugin.strategy.impl

import ru.clevertec.gittaggradleplugin.strategy.TagStrategy

class TagExistsStrategy implements TagStrategy {

    @Override
    String createTagName(String branchName, String latestTagVersion) {
        def tagNumbers = latestTagVersion.find(/\d+\.\d+/).split('\\.')
        def branchMap = [
                'dev'   : incrementMinorVersion(tagNumbers),
                'qa'    : incrementMinorVersion(tagNumbers),
                'stage' : addRCPostfix(tagNumbers),
                'master': incrementMajorVersion(tagNumbers)
        ] as HashMap
        branchMap.getOrDefault(branchName, addSnapshotPostfix(tagNumbers))
    }

    private static def incrementMinorVersion(String[] tagNumbers) {
        tagNumbers[1] = (tagNumbers[1] as Integer) + 1
        'v' + tagNumbers.join('.')
    }

    private static def incrementMajorVersion(String[] tagNumbers) {
        tagNumbers[0] = (tagNumbers[0] as Integer) + 1
        tagNumbers[1] = '0'
        'v' + tagNumbers.join('.')
    }

    private static def addRCPostfix(String[] tagNumbers) {
        def tagName = incrementMinorVersion(tagNumbers)
        "$tagName-rc"
    }

    private static def addSnapshotPostfix(String[] tagNumbers) {
        def tagName = incrementMinorVersion(tagNumbers)
        "$tagName-SNAPSHOT"
    }

}
