package data.dto

import domain.model.Menu
import domain.model.Order

data class OrderDTO(
    val id: Int,
    val tableNumber: Int,
    val menuItemId: Map<Int, Int>,
    val isPaid: Boolean
) {
    fun toDomain(menus: List<Menu>): Order {
        val menuMap = mutableMapOf<Menu, Int>()

        menuItemId.forEach { (menuId,quantity) ->
            val menu = menus.find { it.id == menuId }
            menu?.let { menuMap[it] = quantity }
        }

        return Order(
            id = this.id,
            tableNumber = this.tableNumber,
            menuItems = menuMap,
            isPaid = this.isPaid
        )
    }
}
