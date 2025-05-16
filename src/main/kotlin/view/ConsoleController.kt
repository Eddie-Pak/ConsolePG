package view

class ConsoleController {

    companion object {
        lateinit var currentScreen: BaseScreen
    }

    fun start(screen: BaseScreen) {
        currentScreen = screen

        while (true) {
            currentScreen.display()
            currentScreen.handleInput()
        }
    }
}