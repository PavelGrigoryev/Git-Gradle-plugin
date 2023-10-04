package ru.clevertec.gittaggradleplugin.repository

interface GitRepository {

    String findLatestTagVersion(File projectDir)

    String findCurrentTagVersion(File projectDir)

    String findCurrentBranchName(File projectDir)

    void pushTagToLocalAndOrigin(String tagName, File projectDir)

}