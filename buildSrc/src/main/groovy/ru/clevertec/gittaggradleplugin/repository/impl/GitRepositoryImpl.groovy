package ru.clevertec.gittaggradleplugin.repository.impl

import ru.clevertec.gittaggradleplugin.builder.GitCommandBuilder
import ru.clevertec.gittaggradleplugin.builder.SortOrder
import ru.clevertec.gittaggradleplugin.repository.GitRepository

import static ru.clevertec.gittaggradleplugin.constant.GitTagPluginConstants.SNAPSHOT

class GitRepositoryImpl implements GitRepository {

    @Override
    String findGitVersion() {
        GitCommandBuilder.builder()
                .git()
                .version()
                .execute()
    }

    @Override
    String findUncommittedChanges() {
        GitCommandBuilder.builder()
                .git()
                .diff()
                .execute()
    }

    @Override
    String findLatestTagVersion() {
        GitCommandBuilder.builder()
                .git()
                .describe()
                .tags()
                .abbrev(0)
                .execute()
    }

    @Override
    String findCurrentTagVersion() {
        GitCommandBuilder.builder()
                .git()
                .describe()
                .tags()
                .execute()
    }

    @Override
    String findCurrentBranchName() {
        GitCommandBuilder.builder()
                .git()
                .branch()
                .showCurrent()
                .execute()
    }

    @Override
    String findLatestSnapshotTagByTagVersion(String tagVersion) {
        GitCommandBuilder.builder()
                .git()
                .tag()
                .list()
                .command("${tagVersion.find(/v(\d+)/)}*$SNAPSHOT")
                .sort('version:refname', SortOrder.DESC)
                .execute()
                .lines()
                .findFirst()
                .orElse(tagVersion)
    }

    @Override
    void pushTagToLocalAndOrigin(String tagName) {
        GitCommandBuilder.builder()
                .git()
                .tag()
                .command(tagName)
                .execute()
        GitCommandBuilder.builder()
                .git()
                .push()
                .origin()
                .command(tagName)
                .execute()
    }

}
