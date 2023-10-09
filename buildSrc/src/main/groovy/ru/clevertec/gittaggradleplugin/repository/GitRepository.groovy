package ru.clevertec.gittaggradleplugin.repository

interface GitRepository {

    String findGitVersion()

    String findUncommittedChanges()

    String findLatestTagVersion()

    String findCurrentTagVersion()

    String findCurrentBranchName()

    String findLatestSnapshotTagByTagVersion(String tagVersion)

    void pushTagToLocalAndOrigin(String tagName)

}
