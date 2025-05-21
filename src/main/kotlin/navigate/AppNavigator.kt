package navigate

import data.datasource.MenuDataSource
import data.datasource.MenuDataSourceImpl
import data.repository.MenuRepositoryImpl
import domain.repository.MenuRepository
import view.BaseScreen
import view.HomeScreen
import view.MenuScreen
import view.model.MenuViewModel

class AppNavigator: Navigator {
    private var currentScreen: BaseScreen

    private val menuDataSource: MenuDataSource = MenuDataSourceImpl()
    private val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)



    private val menuViewModel = MenuViewModel(menuRepository)

    private val homeScreen = HomeScreen(this)
    private val menuScreen = MenuScreen(this, menuViewModel)

    init {
        currentScreen = homeScreen
    }

    override fun navigateTo(screenType: ScreenType) {
        currentScreen = when (screenType) {
            ScreenType.Home -> homeScreen
            ScreenType.Menu -> menuScreen
            ScreenType.Store -> TODO()
        }
    }

    override fun getCurrentScreen(): BaseScreen = currentScreen

}