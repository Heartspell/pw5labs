package org.example.pw5labs

import java.sql.ResultSet
import java.sql.Types
import java.time.format.DateTimeFormatter

class PaymentRepository(private val database: DatabaseConnection) {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    fun calculate(input: SalaryInput) {
        database.open().use { connection ->
            connection.autoCommit = false
            connection.prepareCall("{call calculate_payments(?, ?, ?, ?, ?, ?, ?)}").use { call ->
                call.setDouble(1, input.benefit)
                call.setDouble(2, input.profitTax)
                call.setDouble(3, input.profTax)
                call.setDouble(4, input.retirementTax)
                call.setInt(5, input.calendarDays)
                call.setInt(6, input.workDays)
                call.setObject(7, input.date)
                call.execute()
            }
            connection.commit()
        }
    }

    fun findAll(): List<PaymentEntity> {
        database.open().use { connection ->
            connection.autoCommit = false
            connection.prepareCall("{? = call get_payments()}").use { call ->
                call.registerOutParameter(1, Types.OTHER)
                call.execute()
                return (call.getObject(1) as ResultSet).use { rows -> readPayments(rows) }
            }
        }
    }

    private fun readPayments(rows: ResultSet): List<PaymentEntity> {
        val payments = mutableListOf<PaymentEntity>()
        while (rows.next()) {
            val total = rows.getBigDecimal(9).toDouble()
            payments += PaymentEntity(
                rows.getInt(1), rows.getString(2), rows.getString(3), rows.getString(4),
                rows.getString(5), Money.format(rows.getBigDecimal(6).toDouble()),
                "${Money.format(rows.getBigDecimal(7).toDouble())}%",
                rows.getDate(8).toLocalDate().format(dateFormatter), Money.format(total), total
            )
        }
        return payments
    }
}
