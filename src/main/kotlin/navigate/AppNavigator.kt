package navigate

import di.DIContainer
import view.*

class AppNavigator: Navigator {
    private var currentScreen: BaseScreen

    private val di = DIContainer.provider

    private val menuViewModel = di.menuViewModel
    private val storeViewModel = di.storeViewModel
    private val salesViewModel = di.salesViewModel

    private val homeScreen = HomeScreen(this)
    private val menuScreen = MenuScreen(this, menuViewModel)
    private val storeScreen = StoreScreen(this, storeViewModel)
    private val salesScreen = SalesScreen(this, salesViewModel)

    init {
        currentScreen = homeScreen
    }

    override fun navigateTo(screenType: ScreenType) {
        currentScreen = when (screenType) {
            ScreenType.Home -> homeScreen
            ScreenType.Menu -> menuScreen
            ScreenType.Store -> storeScreen
            ScreenType.Sales -> salesScreen
        }
    }

    override fun getCurrentScreen(): BaseScreen = currentScreen

}