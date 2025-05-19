package view

import navigate.Navigator

class ConsoleController(private val navigate: Navigator) {
    fun start() {
        while (true) {
            val currentScreen = navigate.getCurrentScreen()

            currentScreen.display()
            currentScreen.handleInput()
        }
    }
}