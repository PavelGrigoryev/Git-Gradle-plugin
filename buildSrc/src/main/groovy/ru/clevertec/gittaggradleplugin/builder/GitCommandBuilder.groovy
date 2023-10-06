package ru.clevertec.gittaggradleplugin.builder

import static ru.clevertec.gittaggradleplugin.constant.GitTagPluginConstants.*

class GitCommandBuilder {

    List<String> commands

    private GitCommandBuilder() {
        commands = new ArrayList<>()
    }

    static def builder() {
        return new GitCommandBuilder()
    }

    GitCommandBuilder git() {
        this.command(GIT)
    }

    GitCommandBuilder version() {
        this.command(VERSION)
    }

    GitCommandBuilder describe() {
        this.command(DESCRIBE)
    }

    GitCommandBuilder tag() {
        this.command(TAG)
    }

    GitCommandBuilder tags() {
        this.command(TAGS)
    }

    GitCommandBuilder abbrev(int number) {
        this.command(ABBREV + number)
    }

    GitCommandBuilder branch() {
        this.command(BRANCH)
    }

    GitCommandBuilder showCurrent() {
        this.command(SHOW_CURRENT)
    }

    GitCommandBuilder push() {
        this.command(PUSH)
    }

    GitCommandBuilder origin() {
        this.command(ORIGIN)
    }

    GitCommandBuilder diff() {
        this.command(DIFF)
    }

    GitCommandBuilder command(String command) {
        this.commands.add(command)
        this
    }

    String execute() {
        this.commands.execute()
                .in
                .text
                .trim()
    }

}
