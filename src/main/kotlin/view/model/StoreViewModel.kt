package view.model

import domain.model.Menu
import domain.model.Order
import domain.repository.StoreRepository

class StoreViewModel(private val repository: StoreRepository) {
    fun getMenuList(): List<Menu> = repository.getAllMenu()

    fun getAllOrder(): List<Order> = repository.getOrders()

    fun getOrderByTable(tableNumber: Int): Order? = repository.getOrderByTable(tableNumber)

    fun addOrder(tableNumber: Int, menuItems: Map<Int, Int>): Order = repository.addOrder(tableNumber, menuItems)

    fun updateOrder(tableNumber: Int, menuId: Int, newQuantity: Int): Order? = repository.updateOrder(tableNumber, menuId, newQuantity)
}