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

    fun formatPaymentCompleteMessage(): String {
        return """
            === 결제가 완료되었습니다 ===
            테이블: ${tableNumber}번
            결제 금액: ${totalAmount}원
            결제 시간: $paymentDate
        """.trimIndent()
    }
}