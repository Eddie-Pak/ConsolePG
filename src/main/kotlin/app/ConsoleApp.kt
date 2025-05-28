package app

import navigate.AppNavigator
import view.ConsoleController

class ConsoleApp {
    fun run() {
        val navigate = AppNavigator()
        val controller = ConsoleController(navigate)

        controller.start()
    }
}