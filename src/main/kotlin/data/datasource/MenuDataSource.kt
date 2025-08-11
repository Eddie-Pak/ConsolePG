package data.datasource

import data.dto.MenuDTO

interface MenuDataSource {
    // 코루틴으로 바꿔야함
    fun getMenuData(): List<MenuDTO>

    fun saveMenuData(menuDto: List<MenuDTO>): Boolean
}