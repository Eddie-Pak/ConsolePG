package domain.repository

import domain.model.Order

interface StoreRepository {
    fun getOrders(): List<Order>
}