package domain.model

import data.dto.OrderDTO
import resources.StoreStrings.MENU_DISPLAY_FORMAT
import resources.StoreStrings.NO_ORDERS_MESSAGE
import resources.StoreStrings.TABLE_ORDER_HEADER
import resources.StoreStrings.TABLE_TOTAL_PRICE

data class Order(
    val id: Int,
    val tableNumber: Int,
    val menuItems: Map<Menu, Int>,
    val isPaid: Boolean = false
) {
    fun toDTO(): OrderDTO {
        return OrderDTO(
            id = this.id,
            tableNumber = this.tableNumber,
            menuItemId = this.menuItems.map { it.key.id to it.value }.toMap(),
            isPaid = this.isPaid
        )
    }
}

fun List<Order>.formatForDisplay(): String {
    if (this.isEmpty()) {
        return NO_ORDERS_MESSAGE
    }

    val groupByTable = this.groupBy { it.tableNumber }

    val orderByTable = groupByTable.entries.sortedBy { it.key }.joinToString("\n") { (tableNumber, orders) ->
        val tableHeader = String.format(TABLE_ORDER_HEADER, tableNumber)

        val allMenuItems = mutableMapOf<Menu, Int>()
        var totalTablePrice = 0

        orders.forEach { order ->
            order.menuItems.forEach { (menu, quantity) ->
                allMenuItems[menu] = (allMenuItems[menu] ?: 0) + quantity
                totalTablePrice += menu.price * quantity
            }
        }

        val menuDetails = allMenuItems.entries.joinToString("\n") { (menu, quantity) ->
            String.format(MENU_DISPLAY_FORMAT, menu.name, quantity)
        }

        val totalTablePriceString = String.format(TABLE_TOTAL_PRICE, totalTablePrice)

        "$tableHeader\n$menuDetails\n\n$totalTablePriceString\n"
    }

    return orderByTable
}
