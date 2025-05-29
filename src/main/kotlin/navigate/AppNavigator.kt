package navigate

import di.DIProvider
import view.*

class AppNavigator: Navigator {
    private var currentScreen: BaseScreen

    private val menuViewModel = DIProvider.menuViewModel
    private val storeViewModel = DIProvider.storeViewModel
    private val salesViewModel = DIProvider.salesViewModel

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