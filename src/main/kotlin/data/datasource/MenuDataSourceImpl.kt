package data.datasource

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import data.dto.MenuDTO
import java.io.File

class MenuDataSourceImpl: MenuDataSource {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val menuListType = Types.newParameterizedType(
        List::class.java,
        MenuDTO::class.java
    )

    private val menuDtoListAdapter = moshi.adapter<List<MenuDTO>>(menuListType)

    private val filePath = "menu_db"

    override fun getMenuData(): List<MenuDTO> {
        try {
            val file = File(filePath)

            if (!file.exists()) {
                file.createNewFile()
                return emptyList()
            }

            val jsonContent = file.reader().use { it.readText() }

            if (jsonContent.isBlank()) return emptyList()

            return menuDtoListAdapter.fromJson(jsonContent) ?: emptyList()

        } catch (e:Exception) {
            return emptyList()
        }
    }

    override fun saveMenuData(menuDto: List<MenuDTO>): Boolean {
        try {
            val file = File(filePath)

            if (!file.exists()) {
                file.createNewFile()
            }

            val jsonContent = menuDtoListAdapter.toJson(menuDto)

            file.writer().use { it.write(jsonContent) }

            return true
        } catch (e: Exception) {
            return false
        }
    }
}