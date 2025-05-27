package data.datasource

import data.dto.SalesDTO

interface SalesDataSource {
    fun getSalesData(): List<SalesDTO>

    fun saveSalesData(sales: List<SalesDTO>): Boolean
}