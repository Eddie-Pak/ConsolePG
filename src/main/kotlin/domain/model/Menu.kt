package domain.model

import data.dto.MenuDTO

data class Menu(
    val id: Int,
    val name: String,
    val price: Int
) {
    fun toDTO(): MenuDTO {
        return MenuDTO(
            id = this.id,
            name = this.name,
            price = this.price
        )
    }
}

fun List<Menu>.formatForDisplay(): String {
    return this.mapIndexed() { index, menu ->
        "${index + 1}. ${menu.name} - ${menu.price}원"
    }.joinToString("\n")
}