package domain.repository

import domain.model.Menu
import domain.model.Order
import domain.model.Sales

interface SalesRepository {
    fun getAllSales(): List<Sales>

    fun getAllMenu(): List<Menu>

    fun getAllOrder(): List<Order>

    fun getOrderByTable(tableNumber: Int): Order?

    fun processPayment(tableNumber: Int): Sales
}