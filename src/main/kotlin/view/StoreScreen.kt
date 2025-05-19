package view

import view.model.MenuViewModel
import view.model.OrderViewModel

class StoreScreen(
    private val orderViewModel: OrderViewModel,
    private val menuViewModel: MenuViewModel,
    private val getHomeScreen: () -> HomeScreen? = { null }
) : BaseScreen {
    override fun display() {
        TODO("Not yet implemented")
    }

    override fun handleInput() {
        TODO("Not yet implemented")
    }
}