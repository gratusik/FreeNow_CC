package build

object Modules {
    const val APP = ":app"

    object Features {
        const val CREDENTIALS = ":features:credentials"
        const val HOME = ":features:home"
        const val COMIC_MORE = ":features:more"
    }

    object Common {
        const val UI = ":commons:ui"
        const val CORE = ":commons:core"
//        const val TEST_LIBS = ":common:testLibs"
    }
}