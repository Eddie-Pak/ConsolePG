package view

import navigate.Navigator
import navigate.ScreenType
import resources.CommonStrings.INVALID_SELECTION
import resources.CommonStrings.SELECTION_PROMPT
import resources.CommonStrings.SEPARATOR_LINE
import resources.HomeStrings.MENU_OPTIONS
import resources.HomeStrings.TITLE
import kotlin.system.exitProcess

class HomeScreen(private val navigate: Navigator) : BaseScreen {
    override fun display() {
        println(TITLE)
        println(MENU_OPTIONS)
        println(SEPARATOR_LINE)
    }

    override fun handleInput() {
        print("\n$SELECTION_PROMPT")

        when (readlnOrNull()) {
            "1" -> navigate.navigateTo(ScreenType.Menu)

            "2" -> navigate.navigateTo(ScreenType.Store)

            "3" -> navigate.navigateTo(ScreenType.Sales)

            "0" -> exitProcess(0)

            else -> println(INVALID_SELECTION)
        }
    }
}