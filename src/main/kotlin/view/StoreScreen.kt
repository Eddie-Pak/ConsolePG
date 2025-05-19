package view

import view.model.MenuViewModel
import view.model.StoreViewModel

class StoreScreen(
    private val storeViewModel: StoreViewModel,
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