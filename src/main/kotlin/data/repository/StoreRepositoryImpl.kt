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
    private fun getAllMenu(): List<Menu> = menuDataSource.getMenuData().map { it.toDomain() }

    override fun getOrders(): List<Order> {
        val allMenu = getAllMenu()
        return storeDataSource.getOrderData().map { dto -> dto.toDomain(allMenu) }
    }
}