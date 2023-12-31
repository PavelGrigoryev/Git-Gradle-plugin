package ru.clevertec.gittaggradleplugin.factory.impl

import ru.clevertec.gittaggradleplugin.factory.TagFactory
import ru.clevertec.gittaggradleplugin.model.Branch
import ru.clevertec.gittaggradleplugin.repository.impl.GitRepositoryImpl

import static ru.clevertec.gittaggradleplugin.constant.GitTagPluginConstants.RC
import static ru.clevertec.gittaggradleplugin.constant.GitTagPluginConstants.SNAPSHOT

class TagExistsFactory implements TagFactory {

    def gitRepository = new GitRepositoryImpl()

    @Override
    String createTagName(String branchName, String latestTagVersion) {
        switch (branchName) {
            case Branch.DEV.getName():
                latestTagVersion = gitRepository.findLatestDevAndQATagByTagVersion(latestTagVersion)
                def tagNumbers = findAndSplitTagVersionByDot(latestTagVersion)
                incrementMinorVersion(tagNumbers)
                break
            case Branch.QA.getName():
                latestTagVersion = gitRepository.findLatestDevAndQATagByTagVersion(latestTagVersion)
                def tagNumbers = findAndSplitTagVersionByDot(latestTagVersion)
                incrementMinorVersion(tagNumbers)
                break
            case Branch.STAGE.getName():
                def tagNumbers = findAndSplitTagVersionByDot(latestTagVersion)
                addRCPostfix(tagNumbers)
                break
            case Branch.MASTER.getName():
                def tagNumbers = findAndSplitTagVersionByDot(latestTagVersion)
                incrementMajorVersion(tagNumbers)
                break
            default:
                latestTagVersion = gitRepository.findLatestSnapshotTagByTagVersion(latestTagVersion)
                def tagNumbers = findAndSplitTagVersionByDot(latestTagVersion)
                addSnapshotPostfix(tagNumbers)
                break
        }
    }

    private static def incrementMinorVersion(String[] tagNumbers) {
        tagNumbers[1] = (tagNumbers[1] as int) + 1
        "v${tagNumbers.join('.')}"
    }

    private static def incrementMajorVersion(String[] tagNumbers) {
        tagNumbers[0] = (tagNumbers[0] as int) + 1
        tagNumbers[1] = '0'
        "v${tagNumbers.join('.')}"
    }

    private static def addRCPostfix(String[] tagNumbers) {
        def tagName = incrementMinorVersion(tagNumbers)
        "$tagName$RC"
    }

    private static def addSnapshotPostfix(String[] tagNumbers) {
        def tagName = incrementMinorVersion(tagNumbers)
        "$tagName$SNAPSHOT"
    }

    private static def findAndSplitTagVersionByDot(String latestTagVersion) {
        latestTagVersion.find(/\d+\.\d+/).split('\\.')
    }

}
