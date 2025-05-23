package domain.repository

import domain.model.Menu
import domain.model.Order

interface StoreRepository {
    fun getAllMenu(): List<Menu>

    fun getOrders(): List<Order>

    fun getOrderByTable(tableNumber: Int): Order?

    fun addOrder(tableNumber: Int, menuItems: Map<Int, Int>): Order

    fun updateOrder(tableNumber: Int, menuId: Int, newQuantity: Int): Order?
}