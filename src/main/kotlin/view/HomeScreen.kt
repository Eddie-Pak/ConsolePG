package view

import kotlin.system.exitProcess

class HomeScreen(private val menuScreen: MenuScreen) : BaseScreen {
    override fun display() {
        println("Welcome")
        println("[1] 메뉴관리 [2]매장관리 [0] 종료")
    }

    override fun handleInput() {
        when (readlnOrNull()) {
            "1" -> ConsoleController.currentScreen = menuScreen

            "2" -> {}

            "0" -> exitProcess(0)
            else -> println("잘못된 선택")
        }
    }
}