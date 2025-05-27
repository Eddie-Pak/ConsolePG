package navigate

import data.datasource.*
import data.repository.MenuRepositoryImpl
import data.repository.SalesRepositoryImpl
import data.repository.StoreRepositoryImpl
import domain.repository.MenuRepository
import domain.repository.SalesRepository
import domain.repository.StoreRepository
import view.*
import view.model.MenuViewModel
import view.model.SalesViewModel
import view.model.StoreViewModel

class AppNavigator: Navigator {
    private var currentScreen: BaseScreen

    private val menuDataSource: MenuDataSource = MenuDataSourceImpl()
    private val storeDataSource: StoreDataSource = StoreDataSourceImpl()
    private val salesDataSource: SalesDataSource = SalesDataSourceImpl()

    private val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
    private val storeRepository: StoreRepository = StoreRepositoryImpl(menuDataSource, storeDataSource)
    private val salesRepository: SalesRepository = SalesRepositoryImpl(menuDataSource, storeDataSource, salesDataSource)

    private val menuViewModel = MenuViewModel(menuRepository)
    private val storeViewModel = StoreViewModel(storeRepository)
    private val salesViewModel = SalesViewModel(salesRepository)

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