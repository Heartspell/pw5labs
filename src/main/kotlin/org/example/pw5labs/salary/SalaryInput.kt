package org.example.pw5labs.salary

import java.time.LocalDate

data class SalaryInput(
    val benefit: Double,
    val profitTax: Double,
    val profTax: Double,
    val retirementTax: Double,
    val calendarDays: Int,
    val workDays: Int,
    val date: LocalDate
) {
    companion object {
        fun fromFields(
            benefit: String, profitTax: String, profTax: String,
            retirementTax: String, calendarDays: String, workDays: String,
            date: LocalDate?
        ) = SalaryInput(
            percent(benefit, "Bonus"), percent(profitTax, "Income tax"),
            percent(profTax, "Union fee"), percent(retirementTax, "Social fund"),
            calendarDays.toIntOrNull() ?: error("Calendar days must be a whole number"),
            workDays.toIntOrNull() ?: error("Working days must be a whole number"),
            date ?: error("Select a payment date")
        ).also {
            require(it.calendarDays > 0) { "Calendar days must be greater than zero" }
            require(it.workDays in 0..it.calendarDays) { "Working days cannot exceed calendar days" }
            require(it.profitTax + it.profTax + it.retirementTax <= 100) { "Total taxes cannot exceed 100%" }
        }

        private fun percent(value: String, name: String) = value.replace(',', '.').toDoubleOrNull()?.also {
            require(it in 0.0..100.0) { "$name must be between 0 and 100%" }
        } ?: error("$name must be a number")
    }
}
