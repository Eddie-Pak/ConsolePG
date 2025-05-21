package data.datasource

import data.dto.OrderDTO

interface StoreDataSource {
    fun getOrderData(): List<OrderDTO>

    fun saveOrderData(orders: List<OrderDTO>): Boolean
}