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
            percent(benefit, "Премия"), percent(profitTax, "Подоходный налог"),
            percent(profTax, "Профсоюзный взнос"), percent(retirementTax, "Социальный фонд"),
            calendarDays.toIntOrNull() ?: error("Календарные дни должны быть целым числом"),
            workDays.toIntOrNull() ?: error("Рабочие дни должны быть целым числом"),
            date ?: error("Выберите дату выплаты")
        ).also {
            require(it.calendarDays > 0) { "Количество календарных дней должно быть больше нуля" }
            require(it.workDays in 0..it.calendarDays) { "Рабочих дней не может быть больше календарных" }
            require(it.profitTax + it.profTax + it.retirementTax <= 100) { "Сумма налогов не может быть больше 100%" }
        }

        private fun percent(value: String, name: String) = value.replace(',', '.').toDoubleOrNull()?.also {
            require(it in 0.0..100.0) { "$name должен быть от 0 до 100%" }
        } ?: error("$name должен быть числом")
    }
}
