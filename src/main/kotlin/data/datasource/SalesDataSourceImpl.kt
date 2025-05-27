package data.datasource

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import data.dto.SalesDTO
import java.io.File

class SalesDataSourceImpl : SalesDataSource {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val salesListType = Types.newParameterizedType(
        List::class.java,
        SalesDTO::class.java
    )

    private val salesDtoListAdapter = moshi.adapter<List<SalesDTO>>(salesListType)

    private val filePath = "sales_db"

    override fun getSalesData(): List<SalesDTO> {
        try {
            val file = File(filePath)

            if (!file.exists()) {
                file.createNewFile()
                return emptyList()
            }

            val jsonContent = file.reader().use { it.readText() }

            if (jsonContent.isBlank()) return emptyList()

            return salesDtoListAdapter.fromJson(jsonContent) ?: emptyList()

        } catch (e: Exception) {
            return emptyList()
        }
    }

    override fun saveSalesData(sales: List<SalesDTO>): Boolean {
        try {
            val file = File(filePath)

            if (!file.exists()) {
                file.createNewFile()
            }

            val jsonContent = salesDtoListAdapter.toJson(sales)

            file.writer().use { it.write(jsonContent) }

            return true
        } catch (e: Exception) {
            return false
        }
    }
}