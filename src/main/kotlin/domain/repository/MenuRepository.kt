package domain.repository

import domain.model.Menu

interface MenuRepository {
    /**메뉴 목록조회*/
    fun getMenuList(): List<Menu>

    /**메뉴 추가*/
    fun addMenu(name: String, price: Int): Menu

    /**메뉴 수정*/
    fun updateMenu(id: Int, price: Int): Menu

    /**메뉴 삭제*/
    fun deleteMenu(id: Int): Boolean
}