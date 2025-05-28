package data.repository

import data.datasource.MenuDataSource
import data.datasource.StoreDataSource
import domain.model.Menu
import domain.model.Order
import domain.repository.StoreRepository

class StoreRepositoryImpl(
    private val menuDataSource: MenuDataSource,
    private val storeDataSource: StoreDataSource
) : StoreRepository {
    override fun getAllMenu(): List<Menu> = menuDataSource.getMenuData().map { it.toDomain() }

    override fun getOrders(): List<Order> {
        val allMenu = getAllMenu()
        return storeDataSource.getOrderData().map { dto -> dto.toDomain(allMenu) }
    }

    override fun getOrderByTable(tableNumber: Int): Order? {
        val allMenu = getAllMenu()
        val orderDto = storeDataSource.getOrderData().find {
            it.tableNumber == tableNumber && !it.isPaid
        }

        return orderDto?.toDomain(allMenu)
    }

    override fun addOrder(tableNumber: Int, menuItems: Map<Int, Int>): Order {
        val allMenu = getAllMenu()
        val orderDtoList = storeDataSource.getOrderData().toMutableList()

        val existingOrderIndex = orderDtoList.indexOfFirst {
            it.tableNumber == tableNumber && !it.isPaid
        }

        if (existingOrderIndex != -1) {
            val existingOrder = orderDtoList[existingOrderIndex]
            val updatedMenuItems = existingOrder.menuItemId.toMutableMap()

            menuItems.forEach { (menuId, quantity) ->
                updatedMenuItems[menuId] = (updatedMenuItems[menuId] ?: 0) + quantity
            }

            val updatedOrder = existingOrder.copy(menuItemId = updatedMenuItems)

            orderDtoList[existingOrderIndex] = updatedOrder
            storeDataSource.saveOrderData(orderDtoList)

            return updatedOrder.toDomain(allMenu)
        } else {
            val newId = if (orderDtoList.isEmpty()) 1 else orderDtoList.maxOf { it.id } + 1

            val menuMap = mutableMapOf<Menu, Int>()
            menuItems.forEach { (menuId, quantity) ->
                val menu = allMenu.find { it.id == menuId }
                menu?.let { menuMap[it] = quantity }
            }

            val newOrder = Order(
                id = newId,
                tableNumber = tableNumber,
                menuItems = menuMap,
                isPaid = false
            )

            orderDtoList.add(newOrder.toDTO())
            storeDataSource.saveOrderData(orderDtoList)

            return newOrder
        }
    }

    override fun updateOrder(tableNumber: Int, menuId: Int, newQuantity: Int): Order? {
        val allMenu = getAllMenu()
        val orderDtoList = storeDataSource.getOrderData().toMutableList()

        val orderIndex = orderDtoList.indexOfFirst {
            it.tableNumber == tableNumber && !it.isPaid
        }

        val existingOrder = orderDtoList[orderIndex]
        val updatedMenuItems = existingOrder.menuItemId.toMutableMap()

        if (newQuantity <= 0) {
            updatedMenuItems.remove(menuId)
        } else {
            updatedMenuItems[menuId] = newQuantity
        }

        if (updatedMenuItems.isEmpty()) {
            orderDtoList.removeAt(orderIndex)
            storeDataSource.saveOrderData(orderDtoList)
            return null
        } else {
            val updatedOrder = existingOrder.copy(menuItemId = updatedMenuItems)
            orderDtoList[orderIndex] = updatedOrder
            storeDataSource.saveOrderData(orderDtoList)
            return updatedOrder.toDomain(allMenu)
        }
    }
}