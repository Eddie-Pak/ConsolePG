package data.datasource

import data.dto.MenuDTO

interface MenuDataSource {
    fun getMenuData(): List<MenuDTO>

    fun saveMenuData(menuDto: List<MenuDTO>): Boolean
}