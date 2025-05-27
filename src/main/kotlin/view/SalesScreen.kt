package view

import navigate.Navigator
import navigate.ScreenType
import view.model.SalesViewModel
import java.time.LocalDate

class SalesScreen(
    private val navigate: Navigator,
    private val viewModel: SalesViewModel
) : BaseScreen {
    override fun display() {
        println("======== 메출 관리 ========")
        println("1.결제하기 2.매출확인 0. 홈이동")
        println("==========================")
    }

    override fun handleInput() {
        print("\n선택 번호: ")

        when (readlnOrNull()) {
            "0" -> {
                println("==========================")
                println("          홈 이동          ")
                println("==========================")

                navigate.navigateTo(ScreenType.Home)
            }

            "1" -> processPayment()

            "2" -> showSalesReport()

            else -> println("잘못된 입력입니다. 다시 선택해주세요.")
        }
    }

    private fun processPayment() {
        println("\n======== 결제 하기 ========")

        val allOrderList = viewModel.getAllOrder()

        if (allOrderList.isEmpty()) {
            println("결제할 주문이 없습니다.")
            print("\n엔터키를 누르면 매출관리로 돌아갑니다.")
            readlnOrNull()
            return
        }

        println("\n=== 주문이 있는 테이블 목록 ===")

        allOrderList.sortedBy { it.tableNumber }.forEach { order ->
            println("  =${order.tableNumber}번 테이블=")

            var totalPrice = 0

            order.menuItems.forEach { (menu, quantity) ->
                println("  ${menu.name} - ${quantity}개")
                totalPrice += menu.price * quantity
            }
            println("  총 액: ${totalPrice}원\n")
        }

        try {
            print("\n결제할 테이블번호: ")
            val tableNumber = readln().toIntOrNull()
            require(tableNumber != null && tableNumber in 1..7) {"유효한 테이블 번호를 입력해주세요."}

            val orderByTable = viewModel.getOrderByTable(tableNumber)
            if (orderByTable == null) {
                println("${tableNumber}번 테이블에 결제할 주문이 없습니다.")
                print("\n엔터키를 누르면 매출관리로 돌아갑니다.")
                readlnOrNull()
                return
            }

            val totalPriceByTable = orderByTable.menuItems.entries.sumOf { (menu, quantity) ->
                menu.price * quantity
            }

            println("\n=== ${tableNumber}번 테이블 주문 내역 ===")
            orderByTable.menuItems.forEach { (menu, quantity) ->
                println("${menu.name} - ${quantity}개")
            }
            println("\n총 결제 금액: ${totalPriceByTable}원")

            print("\n결제를 진행하시겠습니까? (Y/N): ")
            val confirm = readlnOrNull()?.trim()?.uppercase()

            if (confirm == "Y") {
                val sales = viewModel.processPayment(tableNumber)

                println("\n=== 결제가 완료되었습니다 ===")
                println("테이블: ${sales.tableNumber}번")
                println("결제 금액: ${sales.totalAmount}원")
                println("결제 시간: ${sales.paymentDate}")
                println()
            } else {
                println("\n결제가 취소되었습니다.")
            }

        } catch (e: Exception) {
            println("오류: ${e.message}")
        }
    }

    private fun showSalesReport() {
        println("\n======== 매출 확인 ========")
        println("1.월별매출 2.일별매출 3.메뉴별매출")
        println("==========================")

        print("\n선택 번호: ")

        when (readlnOrNull()) {
            "1" -> showMonthlySales()

            "2" -> showDailySales()

            "3" -> showMenuSales()

            else -> println("잘못된 입력입니다.")
        }
    }

    private fun showMonthlySales() {
        val currentYear = LocalDate.now().year
        val currentMonth = LocalDate.now().monthValue
        val monthlySales = viewModel.getMonthlySales(currentYear)

        println("\n======== ${currentYear}년 월별 매출 (1월~${currentMonth}월) ========")

        if (monthlySales.isEmpty()) {
            println("${currentYear}년 매출 데이터가 없습니다.")
        } else {
            var totalYearToDateSales = 0

            for (month in 1..currentMonth) {
                val sales = monthlySales[month] ?: 0
                totalYearToDateSales += sales
                println("${month}월: ${String.format("%,d", sales)}원")
            }

            println("\n${currentYear}년 ${currentMonth}월까지 총 매출: ${String.format("%,d", totalYearToDateSales)}원")
        }

        print("\n엔터키를 누르면 매출관리로 돌아갑니다.")
        readlnOrNull()
    }

    private fun showDailySales() {
        val currentYear = LocalDate.now().year
        val currentMonth = LocalDate.now().monthValue

        println("\n======== 일별 매출 조회 ========")
        println("조회 가능한 월: 1월 ~ ${currentMonth}월")
        print("조회할 월을 입력하세요 (1~${currentMonth}): ")

        try {
            val selectedMonth = readln().toIntOrNull()
            require(selectedMonth != null && selectedMonth in 1..currentMonth) {
                "1월부터 ${currentMonth}월까지만 선택 가능합니다."
            }

            val dailySales = viewModel.getDailySales(currentYear, selectedMonth)

            println("\n======== ${currentYear}년 ${selectedMonth}월 일별 매출 ========")

            if (dailySales.isEmpty()) {
                println("${currentYear}년 ${selectedMonth}월 매출 데이터가 없습니다.")
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
                        weekSales.add("${day}일: ${String.format("%,d", sales)}원")
                    }

                    println(weekSales.joinToString(" | "))
                }

                println("\n${currentYear}년 ${selectedMonth}월 총 매출: ${String.format("%,d", totalMonthSales)}원")
            }
        } catch (e: Exception) {
            println("오류: ${e.message}")
        }

        print("\n엔터키를 누르면 매출관리로 돌아갑니다.")
        readlnOrNull()
    }

    private fun showMenuSales() {
        val currentYear = LocalDate.now().year
        val menuSales = viewModel.getMenuSales(currentYear)

        println("\n======== ${currentYear}년 메뉴별 판매량 ========")

        if (menuSales.isEmpty()) {
            println("${currentYear}년 메뉴 판매 데이터가 없습니다.")
        } else {
            val sortedMenuSales = menuSales.toList().sortedByDescending { it.second }

            var rank = 1
            sortedMenuSales.forEach { (menu, quantity) ->
                val totalRevenue = menu.price * quantity
                println("${rank}위. ${menu.name} - 판매량:${quantity}개 / 총매출: ${String.format("%,d", totalRevenue)}원)")
                rank++
            }
        }

        print("\n엔터키를 누르면 매출관리로 돌아갑니다.")
        readlnOrNull()
    }
}