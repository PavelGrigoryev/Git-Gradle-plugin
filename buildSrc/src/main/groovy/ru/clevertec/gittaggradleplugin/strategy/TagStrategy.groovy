package ru.clevertec.gittaggradleplugin.strategy

interface TagStrategy {

    String createTagNameByBranchName(String branchName, String latestTagVersion)

    void saveTagToLocalAndRemote(String tagName, File projectDir)

}