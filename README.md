# Git-Tag plugin (код плагина в [buildSrc](buildSrc/src/main/groovy/ru/clevertec/gittaggradleplugin))

## Автор: [Grigoryev Pavel](https://pavelgrigoryev.github.io/GrigoryevPavel/)

***

### Инструкция для запуска плагина локально:

* Запустить задачу в корне проекта Git-Gradle-plugin `./gradlew -p buildSrc publishToMavenLocal`
* Добавить в settings.gradle своего проекта:

````groovy
pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}
````

* Добавить в build.gradle своего проекта id и версию плагина:

````groovy
plugins {
    id 'by.grigoryev.pavel.git-tag-plugin' version '1.0.0'
}
````

* Если хотите проверять незакоммиченные изменения в проекте нужно добавить extension(если не добавлять, то по умолчанию
  false):

````groovy
pushTag {
    checkUncommitted = true
}
````

***

### Инструкция по использованию плагина:

* Добавляет в проект задачу pushTag в группу с названием git
* Задачу можно запустить в корне проекта командой `./gradlew pushTag ` либо воспользовавшись Intellij Idea
* Плагин проверяет наличие гита через команду git version, если его нет, то будет выброшено исключение с сообщением:

````text
Git not found on current device
````

* Если есть незакоммиченные изменения в проекте без тегов и extension checkUncommitted = true, то будет выброшено
  исключение с сообщением:

````text
Detected uncommitted changes in repository without tags
````

* Если есть незакоммиченные изменения в проекте с тегами и extension checkUncommitted = true, то будет выброшено
  исключение с сообщением в котором есть номер сборки с постфиксом .uncommitted:

````text
Detected uncommitted changes in :
v0.1-1-g7439bac.uncommitted
````

* Если теги отсутствуют в проекте, то будет присвоен дефолтный тег в зависимости в какой ветке находимся:
    * master, dev, qa -> v0.1
    * stage -> v0.1-rc
    * любая другая ветка -> v0.1-SNAPSHOT
* И будет выведено сообщение в логах в котором есть название присвоенного тега:

````text
The current commit is assigned tag v0.1
````

* Если текущему коммиту уже присвоен тег, то будет выброшено исключение с сообщением в котором есть название уже
  присвоенного тега:

````text
The current state of the project is already tagged v0.1 by git
````

* Если теги есть в проекте, то будет присвоен тег в зависимости в какой ветке находимся:
    * dev, qa ->
        * будет искаться последний тег по мажорной версии без постфиксов -SNAPSHOT и -rc
        * (это сделано для того, если dev и qa идут параллельно из master)
        * инкремент минорной версии -> v0.2
    * master -> инкремент мажорной версии -> v1.0
    * stage -> инкремент минорной версии и добавление постфикса -rc -> v0.2-rc
    * любая другая ветка ->
        * будет искаться последний тег по мажорной версии с постфиксом -SNAPSHOT
        * (это сделано для того, если любых других веток несколько)
        * инкремент минорной версии и добавление постфикса -SNAPSHOT -> v0.2-SNAPSHOT
* И будет выведено сообщение в логах в котором есть название присвоенного тега:

````text
The current commit is assigned tag v1.0
````

***
