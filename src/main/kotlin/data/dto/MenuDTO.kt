package data.dto

import domain.model.Menu

data class MenuDTO(
    val id: Int,
    val name: String,
    val price: Int
) {
    fun toDomain(): Menu {
        return Menu(
            id = this.id,
            name = this.name,
            price = this.price
        )
    }
}
