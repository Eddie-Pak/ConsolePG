package view

import navigate.Navigator
import navigate.ScreenType
import view.model.StoreViewModel

class StoreScreen(
    private val navigate: Navigator,
    private val storeViewModel: StoreViewModel
) : BaseScreen {
    override fun display() {
        println("======== 메장 관리 ========")
        println("1.주문확인 0.홈이동")
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

            else -> {}
        }
    }

    private fun checkOrders() {

    }
}