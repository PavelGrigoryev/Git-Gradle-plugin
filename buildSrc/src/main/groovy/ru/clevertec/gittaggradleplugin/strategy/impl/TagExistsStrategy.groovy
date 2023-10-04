package ru.clevertec.gittaggradleplugin.strategy.impl

import ru.clevertec.gittaggradleplugin.strategy.TagStrategy

import java.math.RoundingMode

class TagExistsStrategy implements TagStrategy {

    @Override
    String createTagName(String branchName, String latestTagVersion) {
        def tagNumber = latestTagVersion.find(/\d+\.\d+/).toBigDecimal()
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

    private static def incrementMinorVersion(BigDecimal tagNumber) {
        tagNumber = tagNumber.add(0.1G)
        "v$tagNumber"
    }

    private static def incrementMajorVersion(BigDecimal tagNumber) {
        if (tagNumber.toString()[2] == '0') {
            tagNumber = tagNumber.add(BigDecimal.ONE)
        } else {
            tagNumber = tagNumber.setScale(0, RoundingMode.CEILING)
                    .add(0.0G)
        }
        "v$tagNumber"
    }

    private static def addRCPostfix(BigDecimal tagNumber) {
        "v$tagNumber-rc"
    }

    private static def addSnapshotPostfix(BigDecimal tagNumber) {
        "v$tagNumber-SNAPSHOT"
    }

}
