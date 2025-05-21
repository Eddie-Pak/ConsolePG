package view.model

import domain.model.Order
import domain.repository.StoreRepository

class StoreViewModel(private val repository: StoreRepository) {
    fun getAllOrder(): List<Order> = repository.getOrders()
}