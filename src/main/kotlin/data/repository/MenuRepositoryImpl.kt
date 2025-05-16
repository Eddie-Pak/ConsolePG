package data.repository

import data.datasource.MenuDataSource
import domain.model.Menu
import domain.repository.MenuRepository

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenuList(): List<Menu> = dataSource.getMenuData().map { it.toDomain() }

    override fun addMenu(name: String, price: Int): Menu {
        val menuDtoList = dataSource.getMenuData().toMutableList()

        val newId = if (menuDtoList.isEmpty()) 1 else menuDtoList.maxOf { it.id } + 1

        val newMenu = Menu(
            id = newId,
            name = name,
            price = price
        )

        menuDtoList.add(newMenu.toDTO())

        dataSource.saveMenuData(menuDtoList)

        return newMenu
    }

    override fun updateMenu(id: Int, price: Int): Menu {
        val menuDtoList = dataSource.getMenuData().toMutableList()

        val index = menuDtoList.indexOfFirst { it.id == id }
        val updateMenu = menuDtoList[index].toDomain().copy(price = price)

        menuDtoList[index] = updateMenu.toDTO()

        dataSource.saveMenuData(menuDtoList)

        return updateMenu
    }

    override fun deleteMenu(id: Int): Boolean {
        val menuDtoList = dataSource.getMenuData().toMutableList()

        val index = menuDtoList.indexOfFirst { it.id == id }

        menuDtoList.removeAt(index)

        dataSource.saveMenuData(menuDtoList)

        return true
    }
}