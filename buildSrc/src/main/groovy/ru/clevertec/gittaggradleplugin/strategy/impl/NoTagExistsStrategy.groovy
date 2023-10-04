package ru.clevertec.gittaggradleplugin.strategy.impl

import ru.clevertec.gittaggradleplugin.builder.GitCommandBuilder
import ru.clevertec.gittaggradleplugin.strategy.TagStrategy

class NoTagExistsStrategy implements TagStrategy {

    @Override
    String createTagNameByBranchName(String branchName, String latestTagVersion) {
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

    @Override
    void saveTagToLocalAndRemote(String tagName, File projectDir) {
        GitCommandBuilder.builder()
                .git()
                .tag()
                .command(tagName)
                .execute(projectDir)
        GitCommandBuilder.builder()
                .git()
                .push()
                .origin()
                .command(tagName)
                .execute(projectDir)
    }

}
