package view

import domain.model.Menu
import domain.model.formatForDisplay
import navigate.Navigator
import navigate.ScreenType
import resources.CommonStrings.ENTER_TO_CONTINUE
import resources.CommonStrings.ERROR_PREFIX
import resources.CommonStrings.HOME_NAVIGATION
import resources.CommonStrings.INVALID_TABLE_NUMBER
import resources.CommonStrings.SELECTION_PROMPT
import resources.CommonStrings.SEPARATOR_LINE
import resources.CommonStrings.TABLE_NUMBER_PROMPT
import resources.StoreStrings.ADD_ORDER_TITLE
import resources.StoreStrings.ALL_ORDERS_CANCELLED
import resources.StoreStrings.CURRENT_ORDER_TITLE
import resources.StoreStrings.MENU_DISPLAY_FORMAT
import resources.StoreStrings.MENU_LIST_DISPLAY_FORMAT
import resources.StoreStrings.MENU_LIST_TITLE
import resources.StoreStrings.MENU_OPTIONS
import resources.StoreStrings.MENU_REMOVED
import resources.StoreStrings.NEW_QUANTITY_PROMPT
import resources.StoreStrings.NEW_TOTAL_AMOUNT
import resources.StoreStrings.NO_MENU_TO_ORDER
import resources.StoreStrings.NO_UNPAID_ORDER
import resources.StoreStrings.ORDER_CHECK_TITLE
import resources.StoreStrings.ORDER_COMPLETE_TITLE
import resources.StoreStrings.ORDER_INPUT_INSTRUCTION
import resources.StoreStrings.ORDER_INPUT_REQUIRED
import resources.StoreStrings.ORDER_PROMPT
import resources.StoreStrings.ORDER_UPDATE_SUCCESS
import resources.StoreStrings.QUANTITY_CHANGED
import resources.StoreStrings.QUANTITY_MIN_REQUIRED
import resources.StoreStrings.RETURN_TO_STORE_MANAGEMENT
import resources.StoreStrings.SELECTED_MENU_INFO
import resources.StoreStrings.SELECT_MENU_TO_UPDATE
import resources.StoreStrings.TABLE_ORDER_INFO
import resources.StoreStrings.TABLE_TOTAL_AMOUNT
import resources.StoreStrings.TITLE
import resources.StoreStrings.UPDATED_ORDER_TITLE
import resources.StoreStrings.UPDATE_ORDER_TITLE
import resources.StoreStrings.VALID_MENU_NUMBER
import resources.StoreStrings.VALID_MENU_RANGE
import resources.StoreStrings.VALID_ORDER_FORMAT_MESSAGE
import resources.StoreStrings.VALID_ORDER_FORMAT_REQUIRED
import resources.StoreStrings.VALID_QUANTITY_INPUT
import view.model.StoreViewModel

class StoreScreen(
    private val navigate: Navigator,
    private val viewModel: StoreViewModel
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

            "1" -> checkOrders()

            "2" -> addOrder()

            "3" -> updateOrder()

            else -> {}
        }
    }

    private fun checkOrders() {
        println("\n$ORDER_CHECK_TITLE")

        val orders = viewModel.getAllOrder()

        println(orders.formatForDisplay())

        print("\n${String.format(ENTER_TO_CONTINUE, RETURN_TO_STORE_MANAGEMENT)}")
        readlnOrNull()
    }

    private fun addOrder() {
        println("\n$ADD_ORDER_TITLE")

        val menuList = viewModel.getMenuList()

        if (menuList.isEmpty()) {
            println(NO_MENU_TO_ORDER)
        } else {
            try {
                print("\n$TABLE_NUMBER_PROMPT")
                val tableNumber = readln().toIntOrNull()
                require(tableNumber != null && tableNumber in 1..7) {INVALID_TABLE_NUMBER}

                println("\n$MENU_LIST_TITLE")
                println(menuList.formatForDisplay())

                println("\n$ORDER_INPUT_INSTRUCTION")
                print(String.format(ORDER_PROMPT, tableNumber))

                val orderInput = readln()
                require(orderInput.isNotEmpty()) { ORDER_INPUT_REQUIRED }

                val menuItems = parseOrder(orderInput, menuList)
                require(menuItems.isNotEmpty()) { VALID_ORDER_FORMAT_REQUIRED }

                val newOrder = viewModel.addOrder(tableNumber, menuItems)

                println("\n$ORDER_COMPLETE_TITLE")
                println(String.format(TABLE_ORDER_INFO, newOrder.tableNumber))

                menuItems.forEach { (menuId, quantity) ->
                    val menu = menuList.find { it.id == menuId }
                    menu?.let {
                        println(String.format(MENU_DISPLAY_FORMAT, menu.name, quantity))
                    }
                }

                val totalPrice = newOrder.menuItems.entries.sumOf { (menu, quantity) ->
                    menu.price * quantity
                }

                println("\n${String.format(TABLE_TOTAL_AMOUNT, totalPrice)}")
            } catch (e: Exception) {
                println(String.format(ERROR_PREFIX, e.message))
            }
        }

        print("\n${String.format(ENTER_TO_CONTINUE, RETURN_TO_STORE_MANAGEMENT)}")
        readlnOrNull()
    }

    private fun updateOrder() {
        println("\n$UPDATE_ORDER_TITLE")

        try {
            print("\n$TABLE_NUMBER_PROMPT")
            val tableNumber = readln().toIntOrNull()
            require(tableNumber != null && tableNumber in 1..7) {INVALID_TABLE_NUMBER}

            val currentOrder = viewModel.getOrderByTable(tableNumber)

            if (currentOrder == null) {
                println(String.format(NO_UNPAID_ORDER, tableNumber))
                print("\n${String.format(ENTER_TO_CONTINUE, RETURN_TO_STORE_MANAGEMENT)}")
                readlnOrNull()
                return
            }

            println("\n${String.format(CURRENT_ORDER_TITLE, tableNumber)}")

            val menuList = currentOrder.menuItems.entries.toList()
            menuList.forEachIndexed { index, (menu, quantity) ->
                println(String.format(MENU_LIST_DISPLAY_FORMAT, index + 1, menu.name, quantity))
            }

            print("\n${String.format(SELECT_MENU_TO_UPDATE, menuList.size)}")
            val menuIndex = readln().toIntOrNull()
            require(menuIndex != null && menuIndex in 1..menuList.size) {VALID_MENU_NUMBER}

            val selectedMenu = menuList[menuIndex - 1]
            val menu = selectedMenu.key
            val currentQuantity = selectedMenu.value

            println("\n${String.format(SELECTED_MENU_INFO, menu.name, currentQuantity)}")
            print(NEW_QUANTITY_PROMPT)

            val newQuantity = readln().toIntOrNull()
            require(newQuantity != null && newQuantity >= 0) {VALID_QUANTITY_INPUT}

            val updatedOrder = viewModel.updateOrder(tableNumber, menu.id, newQuantity)

            if (updatedOrder == null) {
                println("\n${String.format(ALL_ORDERS_CANCELLED, tableNumber)}")
            } else {
                println("\n$ORDER_UPDATE_SUCCESS")

                if (newQuantity == 0) {
                    println(String.format(MENU_REMOVED, menu.name))
                } else {
                    println(String.format(QUANTITY_CHANGED, menu.name, currentQuantity, newQuantity))
                }

                println("\n$UPDATED_ORDER_TITLE")
                updatedOrder.menuItems.forEach { (updatedMenu, quantity) ->
                    println(String.format(MENU_DISPLAY_FORMAT, updatedMenu.name, quantity))
                }

                val newTotalPrice = updatedOrder.menuItems.entries.sumOf { (updatedMenu, quantity) ->
                    updatedMenu.price * quantity
                }
                println("\n${String.format(NEW_TOTAL_AMOUNT, newTotalPrice)}")
            }
        } catch (e: Exception) {
            println(String.format(ERROR_PREFIX, e.message))
        }

        print("\n${String.format(ENTER_TO_CONTINUE, RETURN_TO_STORE_MANAGEMENT)}")
        readlnOrNull()
    }

    private fun parseOrder(input: String, menuList: List<Menu>): Map<Int, Int> {
        val menuItems = mutableMapOf<Int, Int>()

        val orders = input.split(",").map { it.trim() }

        for (order in orders) {
            val parts = order.split("-").map { it.trim() }
            require(parts.size == 2) {VALID_ORDER_FORMAT_MESSAGE}

            val menuIndex = parts[0].toIntOrNull()
            val quantity = parts[1].toIntOrNull()

            require(menuIndex != null && menuIndex in 1..menuList.size) {
                String.format(VALID_MENU_RANGE, menuList.size)
            }

            require(quantity != null && quantity > 0) { QUANTITY_MIN_REQUIRED }

            val menuId = menuList[menuIndex - 1].id

            menuItems[menuId] = (menuItems[menuId] ?: 0) + quantity
        }

        return menuItems
    }
}