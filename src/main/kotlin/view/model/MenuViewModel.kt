package view.model

import domain.model.Menu
import domain.repository.MenuRepository

class MenuViewModel(private val repository: MenuRepository) {
    fun getMenuList(): List<Menu> = repository.getMenuList()

    fun addMenu(name: String, price: Int): Menu = repository.addMenu(name, price)

    fun updateMenu(id: Int, price: Int): Menu = repository.updateMenu(id, price)

    fun deleteMenu(id: Int): Boolean = repository.deleteMenu(id)
}