package view.model

import domain.model.Menu
import domain.model.Order
import domain.model.Sales
import domain.repository.SalesRepository
import java.time.LocalDate

class SalesViewModel(private val repository: SalesRepository) {
    fun getAllOrder(): List<Order> = repository.getAllOrder()

    fun getOrderByTable(tableNumber: Int): Order? = repository.getOrderByTable(tableNumber)

    fun processPayment(tableNumber: Int): Sales = repository.processPayment(tableNumber)

    fun getMonthlySales(year: Int): Map<Int, Int> = repository.getMonthlySales(year)

    fun getDailySales(year: Int, month: Int): Map<Int, Int> = repository.getDailySales(year, month)

    fun getMenuSales(year: Int = LocalDate.now().year): Map<Menu, Int> = repository.getMenuSales(year)
}