import data.datasource.MenuDataSource
import data.datasource.MenuDataSourceImpl
import data.repository.MenuRepositoryImpl
import domain.repository.MenuRepository
import view.ConsoleController
import view.HomeScreen
import view.MenuScreen
import view.model.MenuViewModel

class ConsoleApp {
    fun run() {
        // menu 기능 DI

        val menuDataSource: MenuDataSource = MenuDataSourceImpl()
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val menuViewModel = MenuViewModel(menuRepository)

        val tempsHomeScreen = HomeScreen(MenuScreen(menuViewModel))

        val menuScreen = MenuScreen(menuViewModel, tempsHomeScreen)

        val homeScreen = HomeScreen(menuScreen)

        ConsoleController().start(homeScreen)
    }
}