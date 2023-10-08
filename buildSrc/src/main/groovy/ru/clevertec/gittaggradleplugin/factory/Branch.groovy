package ru.clevertec.gittaggradleplugin.factory

enum Branch {

    DEV('dev'), QA('qa'), STAGE('stage'), MASTER('master')

    private String name

    Branch(String name) {
        this.name = name
    }

    String getName() {
        return name
    }

}
