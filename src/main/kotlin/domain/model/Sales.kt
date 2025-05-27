package domain.model

import data.dto.SalesDTO
import java.time.LocalDate

data class Sales(
    val id: Int,
    val tableNumber: Int,
    val menuItems: Map<Menu, Int>,
    val totalAmount: Int,
    val paymentDate: LocalDate = LocalDate.now()
) {
    fun toDto(): SalesDTO {
        return SalesDTO(
            id = this.id,
            tableNumber = this.tableNumber,
            menuItems = this.menuItems.map { it.key.id to it.value }.toMap(),
            totalAmount = this.totalAmount,
            paymentDate = this.paymentDate.toString()
        )
    }
}

fun List<Sales>.formatForDisplay(): String {
    if (this.isEmpty()) {
        return "결제 내역이 없습니다."
    }

    return this.joinToString("\n") { payment ->
        val header = "===== 결제테이블 ${payment.tableNumber}번 ====="
        val date = "날짜: ${payment.paymentDate}"
        val menuItems = payment.menuItems.entries.joinToString("\n") { (menu, quantity) ->
            "  ${menu.name} - ${quantity}개"
        }
        val total = "총 결제 금액: ${payment.totalAmount}원"

        "$header\n$date\n$menuItems\n\n$total"
    }
}