package ru.clevertec.gittaggradleplugin.builder

class GitCommandBuilder {

    String[] commands

    private GitCommandBuilder(String[] commands) {
        this.commands = commands
    }

    static def builder() {
        return new GitCommandBuilder(new String[0])
    }

    GitCommandBuilder git() {
        command('git')
    }

    GitCommandBuilder describe() {
        command('describe')
    }

    GitCommandBuilder tag() {
        command('tag')
    }

    GitCommandBuilder tags() {
        command('--tags')
    }

    GitCommandBuilder abbrev(int number) {
        command('--abbrev=' + number)
    }

    GitCommandBuilder branch() {
        command('branch')
    }

    GitCommandBuilder showCurrent() {
        command('--show-current')
    }

    GitCommandBuilder command(String command) {
        String[] newCommands = new String[commands.length + 1]
        System.arraycopy(commands, 0, newCommands, 0, commands.length)
        newCommands[commands.length] = command
        new GitCommandBuilder(newCommands)
    }

    String[] build() {
        commands
    }

}
