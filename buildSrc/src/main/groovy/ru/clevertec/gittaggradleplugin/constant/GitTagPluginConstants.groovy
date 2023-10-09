package ru.clevertec.gittaggradleplugin.constant

interface GitTagPluginConstants {

    def LOGO = $/
##############################################################################################################
##                                                                                                          ##
##     ██████╗ ██╗████████╗████████╗ █████╗  ██████╗     ██████╗ ██╗     ██╗   ██╗ ██████╗ ██╗███╗   ██╗    ##
##    ██╔════╝ ██║╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝     ██╔══██╗██║     ██║   ██║██╔════╝ ██║████╗  ██║    ##
##    ██║  ███╗██║   ██║█████╗██║   ███████║██║  ███╗    ██████╔╝██║     ██║   ██║██║  ███╗██║██╔██╗ ██║    ##
##    ██║   ██║██║   ██║╚════╝██║   ██╔══██║██║   ██║    ██╔═══╝ ██║     ██║   ██║██║   ██║██║██║╚██╗██║    ##
##    ╚██████╔╝██║   ██║      ██║   ██║  ██║╚██████╔╝    ██║     ███████╗╚██████╔╝╚██████╔╝██║██║ ╚████║    ## 
##     ╚═════╝ ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝     ╚═╝     ╚══════╝ ╚═════╝  ╚═════╝ ╚═╝╚═╝  ╚═══╝    ##
##                                                                                                          ##
##     developed by Grigoryev Pavel                                                         version 1.0.0   ## 
##############################################################################################################
/$

    def PUSH_TAG = 'pushTag'
    def DEFAULT_TAG_VERSION = 'v0.1'
    def RC = '-rc'
    def SNAPSHOT = '-SNAPSHOT'

    def GIT = 'git'
    def VERSION = 'version'
    def DESCRIBE = 'describe'
    def TAG = 'tag'
    def TAGS = '--tags'
    def ABBREV = '--abbrev='
    def BRANCH = 'branch'
    def SHOW_CURRENT = '--show-current'
    def PUSH = 'push'
    def ORIGIN = 'origin'
    def DIFF = 'diff'
    def LIST = '-l'
    def SORT  = '--sort='

}
