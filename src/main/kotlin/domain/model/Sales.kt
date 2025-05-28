package domain.model

import data.dto.SalesDTO
import resources.SalesStrings.PAYMENT_AMOUNT
import resources.SalesStrings.PAYMENT_COMPLETE_TITLE
import resources.SalesStrings.PAYMENT_TABLE
import resources.SalesStrings.PAYMENT_TIME
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
            $PAYMENT_COMPLETE_TITLE
            ${String.format(PAYMENT_TABLE, tableNumber)}
            ${String.format(PAYMENT_AMOUNT, totalAmount)}
            ${String.format(PAYMENT_TIME, paymentDate)}
        """.trimIndent()
    }
}