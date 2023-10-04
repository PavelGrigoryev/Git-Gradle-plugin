package ru.clevertec.gittaggradleplugin.constant

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

interface GitTagPluginConstants {

    def LOGO = $/
Git-Tag plugin initialized at %s
##############################################################################################################
##                                                                                                          ##
##     ██████╗ ██╗████████╗████████╗ █████╗  ██████╗     ██████╗ ██╗     ██╗   ██╗ ██████╗ ██╗███╗   ██╗    ##
##    ██╔════╝ ██║╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝     ██╔══██╗██║     ██║   ██║██╔════╝ ██║████╗  ██║    ##
##    ██║  ███╗██║   ██║█████╗██║   ███████║██║  ███╗    ██████╔╝██║     ██║   ██║██║  ███╗██║██╔██╗ ██║    ##
##    ██║   ██║██║   ██║╚════╝██║   ██╔══██║██║   ██║    ██╔═══╝ ██║     ██║   ██║██║   ██║██║██║╚██╗██║    ##
##    ╚██████╔╝██║   ██║      ██║   ██║  ██║╚██████╔╝    ██║     ███████╗╚██████╔╝╚██████╔╝██║██║ ╚████║    ## 
##     ╚═════╝ ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝     ╚═╝     ╚══════╝ ╚═════╝  ╚═════╝ ╚═╝╚═╝  ╚═══╝    ##
##                                                                                                          ##
##     developed by Grigoryev Pavel                                                           version 0.1   ## 
##############################################################################################################/$
            .formatted(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))

    def GIT = 'git'
    def DESCRIBE = 'describe'
    def TAG = 'tag'
    def TAGS = '--tags'
    def ABBREV = '--abbrev='
    def BRANCH = 'branch'
    def SHOW_CURRENT = '--show-current'
    def PUSH = 'push'
    def ORIGIN = 'origin'
    def DIFF = 'diff'

}