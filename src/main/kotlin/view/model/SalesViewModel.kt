package view.model

import domain.model.Order
import domain.model.Sales
import domain.repository.SalesRepository

class SalesViewModel(private val repository: SalesRepository) {
    fun getAllOrder(): List<Order> = repository.getAllOrder()

    fun getOrderByTable(tableNumber: Int): Order? = repository.getOrderByTable(tableNumber)

    fun processPayment(tableNumber: Int): Sales = repository.processPayment(tableNumber)
}