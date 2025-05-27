package data.repository

import data.datasource.MenuDataSource
import data.datasource.SalesDataSource
import data.datasource.StoreDataSource
import domain.model.Menu
import domain.model.Order
import domain.model.Sales
import domain.repository.SalesRepository
import java.time.LocalDate

class SalesRepositoryImpl(
    private val menuDataSource: MenuDataSource,
    private val storeDataSource: StoreDataSource,
    private val salesDataSource: SalesDataSource
) : SalesRepository {
    override fun getAllSales(): List<Sales> {
        val allMenus = menuDataSource.getMenuData().map { it.toDomain() }
        return salesDataSource.getSalesData().map { it.toDomain(allMenus) }
    }

    override fun getAllMenu(): List<Menu> = menuDataSource.getMenuData().map { it.toDomain() }

    override fun getAllOrder(): List<Order> {
        val allMenus = getAllMenu()
        return storeDataSource.getOrderData().map { it.toDomain(allMenus) }
    }

    override fun getOrderByTable(tableNumber: Int): Order? {
        val allMenu = getAllMenu()
        val orderDto = storeDataSource.getOrderData().find {
            it.tableNumber == tableNumber && !it.isPaid
        }

        return orderDto?.toDomain(allMenu)
    }

    override fun processPayment(tableNumber: Int): Sales {
        val allMenus = menuDataSource.getMenuData().map { it.toDomain() }

        val orderDtoList = storeDataSource.getOrderData().toMutableList()
        val orderToPayIndex = orderDtoList.indexOfFirst {
            it.tableNumber == tableNumber && !it.isPaid
        }

        val orderToPay = orderDtoList[orderToPayIndex]

        val menuMap = mutableMapOf<Menu, Int>()
        var totalAmount = 0

        orderToPay.menuItemId.forEach { (menuId, quantity) ->
            val menu = allMenus.find { it.id == menuId }
            menu?.let {
                menuMap[it] = quantity
                totalAmount += menu.price * quantity
            }
        }

        val salesDtoList = salesDataSource.getSalesData().toMutableList()
        val newSalesId = if (salesDtoList.isEmpty()) 1 else salesDtoList.maxOf { it.id } + 1

        val sales = Sales(
            id = newSalesId,
            tableNumber = tableNumber,
            menuItems = menuMap,
            totalAmount = totalAmount,
            paymentDate = LocalDate.now()
        )

        salesDtoList.add(sales.toDto())
        salesDataSource.saveSalesData(salesDtoList)

        orderDtoList.removeAt(orderToPayIndex)
        storeDataSource.saveOrderData(orderDtoList)

        return sales
    }

    override fun getMonthlySales(year: Int): Map<Int, Int> {
        val allSales = getAllSales()

        val monthlySales = allSales
            .filter { it.paymentDate.year == year }
            .groupBy { it.paymentDate.monthValue }
            .mapValues { (_, salesList) ->
                salesList.sumOf { it.totalAmount }
            }

        return monthlySales
    }

    override fun getDailySales(year: Int, month: Int): Map<Int, Int> {
        val allSales = getAllSales()

        val dailySales = allSales
            .filter { it.paymentDate.year == year && it.paymentDate.monthValue == month }
            .groupBy { it.paymentDate.dayOfMonth }
            .mapValues { (_, salesList) ->
                salesList.sumOf { it.totalAmount }
            }

        return dailySales
    }

    override fun getMenuSales(year: Int): Map<Menu, Int> {
        val allSales = getAllSales()
        val menuSalesCount = mutableMapOf<Menu, Int>()

        allSales
            .filter { it.paymentDate.year == year }
            .forEach { sales ->
                sales.menuItems.forEach { (menu, quantity) ->
                    menuSalesCount[menu] = (menuSalesCount[menu] ?: 0) + quantity
                }
            }

        return menuSalesCount
    }
}