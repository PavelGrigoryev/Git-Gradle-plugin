package ru.clevertec.gittaggradleplugin.factory

interface TagFactory {

    String createTagName(String branchName, String latestTagVersion)

}
