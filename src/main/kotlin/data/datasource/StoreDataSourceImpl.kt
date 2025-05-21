package data.datasource

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import data.dto.OrderDTO
import java.io.File

class StoreDataSourceImpl : StoreDataSource {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val orderListType = Types.newParameterizedType(
        List::class.java,
        OrderDTO::class.java
    )

    private val orderDtoListAdapter = moshi.adapter<List<OrderDTO>>(orderListType)

    private val filePath = "order_db"

    override fun getOrderData(): List<OrderDTO> {
        try {
            val file = File(filePath)

            if (!file.exists()) {
                file.createNewFile()
                return emptyList()
            }

            val jsonContent = file.reader().use { it.readText() }

            if (jsonContent.isBlank()) return emptyList()

            return orderDtoListAdapter.fromJson(jsonContent) ?: emptyList()

        } catch (e:Exception) {
            return emptyList()
        }
    }

    override fun saveOrderData(orders: List<OrderDTO>): Boolean {
        try {
            val file = File(filePath)

            if (!file.exists()) {
                file.createNewFile()
            }

            val jsonContent = orderDtoListAdapter.toJson(orders)

            file.writer().use { it.write(jsonContent) }

            return true
        } catch (e: Exception) {
            return false
        }
    }
}