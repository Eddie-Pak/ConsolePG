package view

import navigate.Navigator
import navigate.ScreenType
import kotlin.system.exitProcess

class HomeScreen(private val navigate: Navigator) : BaseScreen {
    override fun display() {
        println("Welcome")
        println("[1] 메뉴관리 [2]매장관리 [0] 종료")
    }

    override fun handleInput() {
        when (readlnOrNull()) {
            "1" -> navigate.navigateTo(ScreenType.Menu)

            "2" -> {}

            "0" -> exitProcess(0)
            else -> println("잘못된 선택")
        }
    }
}