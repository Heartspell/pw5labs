package org.example.pw5labs

import java.sql.Connection
import java.sql.DriverManager

class DatabaseConnection {
    fun open(): Connection = DriverManager.getConnection(
        required("SALARY_DB_URL"),
        required("SALARY_DB_USER"),
        required("SALARY_DB_PASSWORD")
    )

    private fun required(name: String): String =
        System.getenv(name) ?: error("Переменная $name не задана")
}
