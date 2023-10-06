package ru.clevertec.gittaggradleplugin.repository.impl

import ru.clevertec.gittaggradleplugin.builder.GitCommandBuilder
import ru.clevertec.gittaggradleplugin.repository.GitRepository

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
