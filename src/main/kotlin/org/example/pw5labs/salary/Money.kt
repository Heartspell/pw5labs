package org.example.pw5labs.salary

import java.math.BigDecimal
import java.math.RoundingMode

object Money {
    fun format(value: Double): String = BigDecimal.valueOf(value)
        .setScale(2, RoundingMode.HALF_UP)
        .toPlainString()
}
