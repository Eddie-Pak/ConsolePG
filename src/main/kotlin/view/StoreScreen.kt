package view

import domain.model.Menu
import domain.model.formatForDisplay
import navigate.Navigator
import navigate.ScreenType
import view.model.StoreViewModel

class StoreScreen(
    private val navigate: Navigator,
    private val viewModel: StoreViewModel
) : BaseScreen {
    override fun display() {
        println("======== 메장 관리 ========")
        println("1.주문확인 2.주문하기 0.홈이동")
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

            "1" -> checkOrders()

            "2" -> addOrder()

            else -> {}
        }
    }

    private fun checkOrders() {
        println("\n======== 주문 확인 ========")

        val orders = viewModel.getAllOrder()

        println(orders.formatForDisplay())

        print("\n엔터키를 누르면 메뉴관리로 돌아갑니다.")
        readlnOrNull()
    }

    private fun addOrder() {
        println("\n======== 주문 하기 ========")

        val menuList = viewModel.getMenuList()

        if (menuList.isEmpty()) {
            println("주문 가능한 메뉴가 없습니다.")
            print("\n엔터키를 누르면 매장관리로 돌아갑니다.")
            readlnOrNull()
            return
        }

        try {
            print("\n테이블 번호 (1~7): ")
            val tableNumber = readln().toIntOrNull()
            require(tableNumber != null && tableNumber in 1..7) {"유효한 테이블 번호를 입력해 주세요."}

            println("\n=== 메뉴 목록 ===")
            menuList.forEachIndexed { index, menu ->
                println("${index + 1}. ${menu.name} - ${menu.price}원")
            }

            println("\n주문을 입력해주세요.")
            println("형식: 메뉴번호-수량, 메뉴번호-수량 (예: 1-2, 3-1)")
            print("주문: ")

            val orderInput = readlnOrNull() ?: ""
            require(orderInput.isNotEmpty()) { "주문을 입력해주세요." }

            val menuItems = parseOrder(orderInput, menuList)
            require(menuItems.isNotEmpty()) { "올바른 주문 형식을 입력해주세요." }

            val newOrder = viewModel.addOrder(tableNumber, menuItems)

            println("\n=== 주문이 완료되었습니다 ===")
            println("테이블 번호: ${newOrder.tableNumber}번 주문내용")

            menuItems.forEach { (menuId, quantity) ->
                val menu = menuList.find { it.id == menuId }
                menu?.let {
                    println("${menu.name} - $quantity")
                }
            }

            val totalPrice = newOrder.menuItems.entries.sumOf { (menu, quantity) ->
                menu.price * quantity
            }

            println("\n테이블 총 금액: ${totalPrice}원")

            print("\n엔터키를 누르면 매장관리로 돌아갑니다.")
            readlnOrNull()
        } catch (e: Exception) {
            println("${e.message}")
        }
    }

    private fun parseOrder(input: String, menuList: List<Menu>): Map<Int, Int> {
        val menuItems = mutableMapOf<Int, Int>()

        val orders = input.split(",").map { it.trim() }

        for (order in orders) {
            val parts = order.split("-").map { it.trim() }
            require(parts.size == 2) {"메뉴를 올바른형식으로 입력해주세요."}

            val menuIndex = parts[0].toIntOrNull()
            val quantity = parts[1].toIntOrNull()

            require(menuIndex != null && menuIndex in 1..menuList.size) {
                "유효한 메뉴 번호(1-${menuList.size})를 입력해주세요."
            }

            require(quantity != null && quantity > 0) { "수량은 1 이상이어야 합니다." }

            val menuId = menuList[menuIndex - 1].id

            menuItems[menuId] = (menuItems[menuId] ?: 0) + quantity
        }

        return menuItems
    }
}