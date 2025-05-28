package domain.model

import data.dto.MenuDTO
import resources.MenuStrings.MENU_DISPLAY_FORMAT
import resources.MenuStrings.MENU_LIST_DISPLAY_FORMAT

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

    fun formatForDisplay(): String {
        return String.format(MENU_DISPLAY_FORMAT, this.name, this.price)
    }
}

fun List<Menu>.formatForDisplay(): String {
    return this.mapIndexed() { index, menu ->
        String.format(MENU_LIST_DISPLAY_FORMAT,index + 1, menu.name, menu.price)
    }.joinToString("\n")
}