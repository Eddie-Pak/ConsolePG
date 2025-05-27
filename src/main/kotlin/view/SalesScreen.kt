package view

import navigate.Navigator
import navigate.ScreenType
import view.model.SalesViewModel

class SalesScreen(
    private val navigate: Navigator,
    private val viewModel: SalesViewModel
) : BaseScreen {
    override fun display() {
        println("======== 메출 관리 ========")
        println("1. 결제하기 0. 홈이동")
        println("==========================")
    }

    override fun handleInput() {
        when (readlnOrNull()) {
            "0" -> {
                println("==========================")
                println("          홈 이동          ")
                println("==========================")

                navigate.navigateTo(ScreenType.Home)
            }

            "1" -> processPayment()

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
}