package ru.clevertec.plugin

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

interface GitTagPluginConstants {

    def LOGO = $/
Git-Tag plugin initialized at %s
###########################################################################
##     _______         _________       _________    ____      _______    ##
##    /  _____|   (_)  |_______|       |_______|   / __ \    /  _____|   ##
##   |  /   ____  | |     | |    ____     | |     | |__| |  |  /   ____  ##
##   |  |  |_  |  | |     | |   |____|    | |     |  __  |  |  |  |_  |  ##
##   \  \____| |  | |     | |             | |     | |  | |  \  \____| |  ##
##    \________|  |_|     |_|             |_|     |_|  |_|   \________|  ##
##                            version 0.1                                ## 
##                   developed by Grigoryev Pavel                        ##
###########################################################################/$
            .formatted(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))

}