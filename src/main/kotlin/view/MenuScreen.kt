package view

import domain.model.formatForDisplay
import navigate.Navigator
import navigate.ScreenType
import resources.CommonStrings.ENTER_TO_CONTINUE
import resources.CommonStrings.ERROR_PREFIX
import resources.CommonStrings.HOME_NAVIGATION
import resources.CommonStrings.INVALID_SELECTION
import resources.CommonStrings.SELECTION_PROMPT
import resources.CommonStrings.SEPARATOR_LINE
import resources.MenuStrings.ADD_MENU_TITLE
import resources.MenuStrings.DELETE_MENU_TITLE
import resources.MenuStrings.ENTER_MENU_NAME
import resources.MenuStrings.ENTER_MENU_PRICE
import resources.MenuStrings.ENTER_NEW_PRICE
import resources.MenuStrings.MENU_ADDED_SUCCESS
import resources.MenuStrings.MENU_DELETE_CONFIRM
import resources.MenuStrings.MENU_DELETE_ERROR
import resources.MenuStrings.MENU_DELETE_SUCCESS
import resources.MenuStrings.MENU_LIST_TITLE
import resources.MenuStrings.MENU_NAME_REQUIRED
import resources.MenuStrings.MENU_OPTIONS
import resources.MenuStrings.MENU_UPDATE_SUCCESS
import resources.MenuStrings.NO_MENU_AVAILABLE
import resources.MenuStrings.NO_MENU_TO_DELETE
import resources.MenuStrings.NO_MENU_TO_UPDATE
import resources.MenuStrings.PRICE_CHANGE_FORMAT
import resources.MenuStrings.RETURN_TO_MENU_MANAGEMENT
import resources.MenuStrings.SELECTED_MENU
import resources.MenuStrings.SELECT_MENU_TO_DELETE
import resources.MenuStrings.SELECT_MENU_TO_UPDATE
import resources.MenuStrings.TITLE
import resources.MenuStrings.UPDATE_MENU_TITLE
import resources.MenuStrings.VALID_MENU_NUMBER_REQUIRED
import resources.MenuStrings.VALID_PRICE_INPUT_REQUIRED
import resources.MenuStrings.VALID_PRICE_REQUIRED
import view.model.MenuViewModel

class MenuScreen(
    private val navigate: Navigator,
    private val viewModel: MenuViewModel,
) : BaseScreen {
    override fun display() {
        println(TITLE)
        println(MENU_OPTIONS)
        println(SEPARATOR_LINE)
    }

    override fun handleInput() {
        print("\n$SELECTION_PROMPT")

        when (readlnOrNull()) {
            "0" -> {
                println(SEPARATOR_LINE)
                println(HOME_NAVIGATION)
                println(SEPARATOR_LINE)

                navigate.navigateTo(ScreenType.Home)
            }

            "1" -> getAllMenu()

            "2" -> addMenu()

            "3" -> updateMenu()

            "4" -> deleteMenu()

            else -> println(INVALID_SELECTION)
        }
    }

    private fun getAllMenu() {
        println("\n$MENU_LIST_TITLE")

        val menuList = viewModel.getMenuList()

        if (menuList.isEmpty()) {
            println(NO_MENU_AVAILABLE)
            println()
        } else {
            println(menuList.formatForDisplay())
        }

        print("\n${String.format(ENTER_TO_CONTINUE, RETURN_TO_MENU_MANAGEMENT)}")
        readlnOrNull()
    }

    private fun addMenu() {
        println("\n$ADD_MENU_TITLE")

        try {
            print(ENTER_MENU_NAME)
            val menuName = readlnOrNull() ?: ""
            require(menuName.isNotEmpty()) { "\n$MENU_NAME_REQUIRED\n" }

            print(ENTER_MENU_PRICE)
            val price = readln().toIntOrNull()
            require(price != null && price > 0) { "\n$VALID_PRICE_REQUIRED\n" }

            val addMenu = viewModel.addMenu(menuName, price)

            println(String.format(MENU_ADDED_SUCCESS, addMenu.name, addMenu.price))
            println()
        } catch (e: Exception) {
            println(String.format(ERROR_PREFIX, e.message))
        }

        print("\n${String.format(ENTER_TO_CONTINUE, RETURN_TO_MENU_MANAGEMENT)}")
        readlnOrNull()
    }

    private fun updateMenu() {
        println("\n$UPDATE_MENU_TITLE")
        println()

        val menuList = viewModel.getMenuList()

        if (menuList.isEmpty()) {
            println(NO_MENU_TO_UPDATE)
            println()
        } else {
            println(menuList.formatForDisplay())

            try {
                print("\n$SELECT_MENU_TO_UPDATE")
                val menuIndex = readln().toIntOrNull() ?: 0
                require(menuIndex in 1..menuList.size) { "\n$VALID_MENU_NUMBER_REQUIRED\n" }

                val selectedMenu = menuList[menuIndex - 1]

                println("\n${String.format(SELECTED_MENU, selectedMenu.name, selectedMenu.price)}")

                print(ENTER_NEW_PRICE)
                val newPrice = readln().toIntOrNull()
                require(newPrice != null && newPrice > 0) { "\n$VALID_PRICE_INPUT_REQUIRED\n" }

                val updateMenu = viewModel.updateMenu(selectedMenu.id, newPrice)

                println("\n$MENU_UPDATE_SUCCESS")
                println(String.format(PRICE_CHANGE_FORMAT, updateMenu.name, selectedMenu.price, updateMenu.price))

            } catch (e: Exception) {
                println(String.format(ERROR_PREFIX, e.message))
            }
        }

        print("\n${String.format(ENTER_TO_CONTINUE, RETURN_TO_MENU_MANAGEMENT)}")
        readlnOrNull()
    }

    private fun deleteMenu() {
        println("\n$DELETE_MENU_TITLE")
        println()

        val menuList = viewModel.getMenuList()

        if (menuList.isEmpty()) {
            println(NO_MENU_TO_DELETE)
            println()
        } else {
            println(menuList.formatForDisplay())

            try {
                print("\n$SELECT_MENU_TO_DELETE")
                val menuIndex = readln().toIntOrNull() ?: 0
                require(menuIndex in 1..menuList.size) { "\n$VALID_MENU_NUMBER_REQUIRED\n" }

                val selectMenu = menuList[menuIndex - 1]

                println(String.format(MENU_DELETE_CONFIRM, selectMenu.name))

                val isDelete = viewModel.deleteMenu(selectMenu.id)

                if (isDelete) {
                    println("\n${String.format(MENU_DELETE_SUCCESS, selectMenu.name)}")
                } else {
                    println("\n$MENU_DELETE_ERROR")
                }
            } catch (e: Exception) {
                println(String.format(ERROR_PREFIX, e.message))
            }

            print("\n${String.format(ENTER_TO_CONTINUE, RETURN_TO_MENU_MANAGEMENT)}")
            readlnOrNull()
        }
    }
}