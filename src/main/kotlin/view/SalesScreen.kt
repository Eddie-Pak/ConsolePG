package view

import navigate.Navigator
import navigate.ScreenType
import resources.CommonStrings.ENTER_TO_CONTINUE
import resources.CommonStrings.ERROR_PREFIX
import resources.CommonStrings.HOME_NAVIGATION
import resources.CommonStrings.INVALID_SELECTION
import resources.CommonStrings.INVALID_TABLE_NUMBER
import resources.CommonStrings.SELECTION_PROMPT
import resources.CommonStrings.SEPARATOR_LINE
import resources.SalesStrings.AVAILABLE_MONTHS
import resources.SalesStrings.DAILY_SALES_FORMAT
import resources.SalesStrings.DAILY_SALES_HEADER
import resources.SalesStrings.DAILY_SALES_TITLE
import resources.SalesStrings.MENU_BODY
import resources.SalesStrings.MENU_NAME_AMOUNT
import resources.SalesStrings.MENU_OPTIONS
import resources.SalesStrings.MENU_RANK_FORMAT
import resources.SalesStrings.MENU_SALES_TITLE
import resources.SalesStrings.MONTHLY_SALES_TITLE
import resources.SalesStrings.MONTHLY_TOTAL_SALES
import resources.SalesStrings.MONTH_RANGE_ERROR
import resources.SalesStrings.MONTH_SALES
import resources.SalesStrings.NO_MENU_SALES_DATA
import resources.SalesStrings.NO_MONTHLY_SALES_DATA
import resources.SalesStrings.NO_ORDERS_TO_PAY
import resources.SalesStrings.NO_ORDER_FOR_TABLE
import resources.SalesStrings.NO_YEARLY_SALES_DATA
import resources.SalesStrings.PAYMENT_CANCEL
import resources.SalesStrings.PAYMENT_QUESTION
import resources.SalesStrings.PAYMENT_TITLE
import resources.SalesStrings.RETURN_TO_SALES_MANAGEMENT
import resources.SalesStrings.SALES_REPORT_OPTIONS
import resources.SalesStrings.SALES_REPORT_TITLE
import resources.SalesStrings.SELECT_MONTH_PROMPT
import resources.SalesStrings.SELECT_TABLE_TO_PAY
import resources.SalesStrings.TABLES_WITH_ORDERS
import resources.SalesStrings.TABLE_HEADER
import resources.SalesStrings.TABLE_ORDER_DETAILS
import resources.SalesStrings.TITLE
import resources.SalesStrings.TOTAL_AMOUNT
import resources.SalesStrings.TOTAL_PAYMENT_AMOUNT
import resources.SalesStrings.TOTAL_YEAR_SALES
import view.model.SalesViewModel
import java.time.LocalDate

class SalesScreen(
    private val navigate: Navigator,
    private val viewModel: SalesViewModel
) : BaseScreen {
    override fun display() {
        println(TITLE)
        println(MENU_OPTIONS)
        println(SEPARATOR_LINE)
    }

    override fun handleInput() {
        print("\n$SELECTION_PROMPT")

        when (readlnOrNull()) {
            "0" -> {
                println(SEPARATOR_LINE)
                println(HOME_NAVIGATION)
                println(SEPARATOR_LINE)

                navigate.navigateTo(ScreenType.Home)
            }

            "1" -> processPayment()

            "2" -> showSalesReport()

            else -> println(INVALID_SELECTION)
        }
    }

    private fun processPayment() {
        println("\n$PAYMENT_TITLE")

        val allOrderList = viewModel.getAllOrder()

        if (allOrderList.isEmpty()) {
            println(NO_ORDERS_TO_PAY)
            print("\n${String.format(ENTER_TO_CONTINUE, RETURN_TO_SALES_MANAGEMENT)}")
            readlnOrNull()
            return
        }

        println("\n$TABLES_WITH_ORDERS")

        allOrderList.sortedBy { it.tableNumber }.forEach { order ->
            println(String.format(TABLE_HEADER, order.tableNumber))

            var totalPrice = 0

            order.menuItems.forEach { (menu, quantity) ->
                println(String.format(MENU_BODY, menu.name, quantity))
                totalPrice += menu.price * quantity
            }
            println(String.format(TOTAL_AMOUNT, totalPrice))
        }

        try {
            print("\n$SELECT_TABLE_TO_PAY")
            val tableNumber = readln().toIntOrNull()
            require(tableNumber != null && tableNumber in 1..7) {INVALID_TABLE_NUMBER}

            val orderByTable = viewModel.getOrderByTable(tableNumber)

            if (orderByTable == null) {
                println(String.format(NO_ORDER_FOR_TABLE, tableNumber))
            } else {
                val totalPriceByTable = orderByTable.menuItems.entries.sumOf { (menu, quantity) ->
                    menu.price * quantity
                }

                println("\n${String.format(TABLE_ORDER_DETAILS, tableNumber)}")
                orderByTable.menuItems.forEach { (menu, quantity) ->
                    println(String.format(MENU_NAME_AMOUNT, menu.name, quantity))
                }
                println("\n${String.format(TOTAL_PAYMENT_AMOUNT, totalPriceByTable)}")

                print("\n$PAYMENT_QUESTION")
                val confirm = readlnOrNull()?.trim()?.uppercase()

                if (confirm == "Y") {
                    val sales = viewModel.processPayment(tableNumber)

                    println(sales.formatPaymentCompleteMessage())
                } else {
                    println("\n$PAYMENT_CANCEL")
                }
            }
        } catch (e: Exception) {
            println(String.format(ERROR_PREFIX, e.message))
        }

        print("\n${String.format(ENTER_TO_CONTINUE, RETURN_TO_SALES_MANAGEMENT)}")
        readlnOrNull()
    }

    private fun showSalesReport() {
        println("\n$SALES_REPORT_TITLE")
        println(SALES_REPORT_OPTIONS)
        println(SEPARATOR_LINE)

        print("\n$SELECTION_PROMPT")

        when (readlnOrNull()) {
            "1" -> showMonthlySales()

            "2" -> showDailySales()

            "3" -> showMenuSales()

            else -> println(INVALID_SELECTION)
        }
    }

    private fun showMonthlySales() {
        val currentYear = LocalDate.now().year
        val currentMonth = LocalDate.now().monthValue
        val monthlySales = viewModel.getMonthlySales(currentYear)

        println("\n${String.format(MONTHLY_SALES_TITLE, currentYear, currentMonth)}")

        if (monthlySales.isEmpty()) {
            println(String.format(NO_YEARLY_SALES_DATA, currentYear))
        } else {
            var totalYearToDateSales = 0

            for (month in 1..currentMonth) {
                val sales = monthlySales[month] ?: 0
                totalYearToDateSales += sales
                val formatSales = String.format("%,d", sales)
                println(String.format(MONTH_SALES, month, formatSales))
            }

            val formatTotalDateSales = String.format("%,d", totalYearToDateSales)
            println(String.format(TOTAL_YEAR_SALES, currentYear, currentMonth, formatTotalDateSales))
        }

        print("\n${String.format(ENTER_TO_CONTINUE, RETURN_TO_SALES_MANAGEMENT)}")
        readlnOrNull()
    }

    private fun showDailySales() {
        val currentYear = LocalDate.now().year
        val currentMonth = LocalDate.now().monthValue

        println("\n$DAILY_SALES_TITLE")
        println(String.format(AVAILABLE_MONTHS, currentMonth))
        print(String.format(SELECT_MONTH_PROMPT, currentMonth))

        try {
            val selectedMonth = readln().toIntOrNull()
            require(selectedMonth != null && selectedMonth in 1..currentMonth) {
                String.format(MONTH_RANGE_ERROR, currentMonth)
            }

            val dailySales = viewModel.getDailySales(currentYear, selectedMonth)

            println("\n${String.format(DAILY_SALES_HEADER, currentYear, selectedMonth)}")

            if (dailySales.isEmpty()) {
                println(String.format(NO_MONTHLY_SALES_DATA, currentYear, selectedMonth))
            } else {
                val totalMonthSales = dailySales.values.sum()
                val sortedDailySales = dailySales.toSortedMap()

                val lastDayOfMonth = when (selectedMonth) {
                    4, 6, 9, 11 -> 30
                    2 -> if (currentYear % 4 == 0 && (currentYear % 100 != 0 || currentYear % 400 == 0)) 29 else 28
                    else -> 31
                }

                for (startDay in 1..lastDayOfMonth step 7) {
                    val endDay = minOf(startDay + 6, lastDayOfMonth)
                    val weekSales = mutableListOf<String>()

                    for (day in startDay..endDay) {
                        val sales = sortedDailySales[day] ?: 0
                        val salesByDate = String.format("%,d", sales)
                        weekSales.add(String.format(DAILY_SALES_FORMAT, day, salesByDate))
                    }

                    println(weekSales.joinToString(" | "))
                }
                val formatTotalMonthSales = String.format("%,d", totalMonthSales)
                println(String.format(MONTHLY_TOTAL_SALES, currentYear, selectedMonth, formatTotalMonthSales))
            }
        } catch (e: Exception) {
            println(String.format(ERROR_PREFIX, e.message))
        }

        print("\n${String.format(ENTER_TO_CONTINUE, RETURN_TO_SALES_MANAGEMENT)}")
        readlnOrNull()
    }

    private fun showMenuSales() {
        val currentYear = LocalDate.now().year
        val menuSales = viewModel.getMenuSales(currentYear)

        println("\n${String.format(MENU_SALES_TITLE, currentYear)}")

        if (menuSales.isEmpty()) {
            println(String.format(NO_MENU_SALES_DATA, currentYear))
        } else {
            val sortedMenuSales = menuSales.toList().sortedByDescending { it.second }

            var rank = 1
            sortedMenuSales.forEach { (menu, quantity) ->
                val totalRevenue = menu.price * quantity
                val formatTotalRevenue = String.format("%,d", totalRevenue)
                println(String.format(MENU_RANK_FORMAT, rank, menu.name, quantity, formatTotalRevenue))
                rank++
            }
        }

        print("\n${String.format(ENTER_TO_CONTINUE, RETURN_TO_SALES_MANAGEMENT)}")
        readlnOrNull()
    }
}