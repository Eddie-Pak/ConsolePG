package domain.model

import data.dto.OrderDTO

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
        return "현재 모든 테이블에 주문이 없습니다."
    }

    val groupByTable = this.groupBy { it.tableNumber }

    val orderByTable = groupByTable.entries.sortedBy { it.key }.joinToString("\n") { (tableNumber, orders) ->
        val tableHeader = "=${tableNumber}번 테이블="

        val allMenuItems = mutableMapOf<Menu, Int>()
        var totalTablePrice = 0

        orders.forEach { order ->
            order.menuItems.forEach { (menu, quantity) ->
                allMenuItems[menu] = (allMenuItems[menu] ?: 0) + quantity
                totalTablePrice += menu.price * quantity
            }
        }

        val menuDetails = allMenuItems.entries.joinToString("\n") { (menu, quantity) ->
            "${menu.name} - ${quantity}개"
        }

        "$tableHeader\n$menuDetails\n\n총가격: ${totalTablePrice}원"
    }

    return orderByTable
}
