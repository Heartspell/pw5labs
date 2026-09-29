package org.example.pw5labs

import java.sql.Connection
import java.sql.DriverManager

class DatabaseConnection {
    fun open(): Connection = DriverManager.getConnection(
        EnvParser.required("SALARY_DB_URL"),
        EnvParser.required("SALARY_DB_USER"),
        EnvParser.required("SALARY_DB_PASSWORD")
    )
}
