package navigate

import data.datasource.MenuDataSource
import data.datasource.MenuDataSourceImpl
import data.datasource.StoreDataSource
import data.datasource.StoreDataSourceImpl
import data.repository.MenuRepositoryImpl
import data.repository.StoreRepositoryImpl
import domain.repository.MenuRepository
import domain.repository.StoreRepository
import view.BaseScreen
import view.HomeScreen
import view.MenuScreen
import view.StoreScreen
import view.model.MenuViewModel
import view.model.StoreViewModel

class AppNavigator: Navigator {
    private var currentScreen: BaseScreen

    private val menuDataSource: MenuDataSource = MenuDataSourceImpl()
    private val storeDataSource: StoreDataSource = StoreDataSourceImpl()

    private val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
    private val storeRepository: StoreRepository = StoreRepositoryImpl(menuDataSource, storeDataSource)

    private val menuViewModel = MenuViewModel(menuRepository)
    private val storeViewModel = StoreViewModel(storeRepository)

    private val homeScreen = HomeScreen(this)
    private val menuScreen = MenuScreen(this, menuViewModel)
    private val storeScreen = StoreScreen(this, storeViewModel)

    init {
        currentScreen = homeScreen
    }

    override fun navigateTo(screenType: ScreenType) {
        currentScreen = when (screenType) {
            ScreenType.Home -> homeScreen
            ScreenType.Menu -> menuScreen
            ScreenType.Store -> storeScreen
        }
    }

    override fun getCurrentScreen(): BaseScreen = currentScreen

}