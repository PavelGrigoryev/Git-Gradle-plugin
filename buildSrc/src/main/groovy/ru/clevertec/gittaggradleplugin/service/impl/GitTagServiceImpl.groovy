package ru.clevertec.gittaggradleplugin.service.impl

import ru.clevertec.gittaggradleplugin.exception.AlreadyTaggedException
import ru.clevertec.gittaggradleplugin.exception.GitNotFoundException
import ru.clevertec.gittaggradleplugin.exception.UncommittedChangesException
import ru.clevertec.gittaggradleplugin.repository.impl.GitRepositoryImpl
import ru.clevertec.gittaggradleplugin.service.GitTagService
import ru.clevertec.gittaggradleplugin.strategy.impl.NoTagExistsStrategy
import ru.clevertec.gittaggradleplugin.strategy.impl.TagExistsStrategy

class GitTagServiceImpl implements GitTagService {

    def gitRepository = new GitRepositoryImpl()
    def noTagExistsStrategy = new NoTagExistsStrategy()
    def tagExistsStrategy = new TagExistsStrategy()

    @Override
    void pushTagByProjectDir() {
        def gitVersion = gitRepository.findGitVersion()
        if (gitVersion.isEmpty()) {
            throw new GitNotFoundException('Git not found on current device')
        }
        def uncommitted = gitRepository.findUncommittedChanges()
        if (!uncommitted.isEmpty()) {
            def tagVersion = gitRepository.findCurrentTagVersion()
            def exceptionMessage = tagVersion.isEmpty()
                    ? 'Detected uncommitted changes in repository without tags'
                    : "Detected uncommitted changes in :\n$tagVersion" + '.uncommitted'
            throw new UncommittedChangesException(exceptionMessage)
        }
        def latestTagVersion = gitRepository.findLatestTagVersion()
        def branchName = gitRepository.findCurrentBranchName()
        if (latestTagVersion.isEmpty()) {
            def tagName = noTagExistsStrategy.createTagName(branchName, latestTagVersion)
            gitRepository.pushTagToLocalAndOrigin(tagName)
            return
        }
        def currentTagVersion = gitRepository.findCurrentTagVersion()
        if (latestTagVersion == currentTagVersion) {
            throw new AlreadyTaggedException("The current state of the project is already tagged $currentTagVersion by git")
        } else {
            def tagName = tagExistsStrategy.createTagName(branchName, latestTagVersion)
            gitRepository.pushTagToLocalAndOrigin(tagName)
        }
    }

}
