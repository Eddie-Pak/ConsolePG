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
