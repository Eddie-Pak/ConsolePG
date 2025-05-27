package data.dto

import domain.model.Menu
import domain.model.Sales
import java.time.LocalDate

data class SalesDTO(
    val id: Int,
    val tableNumber: Int,
    val menuItems: Map<Int, Int>,
    val totalAmount: Int,
    val paymentDate: String
) {
    fun toDomain(menus: List<Menu>): Sales {
        val menuMap = mutableMapOf<Menu, Int>()

        menuItems.forEach { (menuId, quantity) ->
            val menu = menus.find { it.id == menuId }
            menu?.let { menuMap[it] = quantity }
        }

        return Sales(
            id = this.id,
            tableNumber = this.tableNumber,
            menuItems = menuMap,
            totalAmount = this.totalAmount,
            paymentDate = LocalDate.parse(this.paymentDate)
        )
    }
}