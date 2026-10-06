package org.example.pw5labs.salary

import org.example.pw5labs.common.EnvParser
import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties

class DatabaseConnection {
    fun open(): Connection {
        val properties = Properties().apply {
            setProperty("user", EnvParser.required("SALARY_DB_USER"))
            setProperty("password", EnvParser.required("SALARY_DB_PASSWORD"))
            setProperty("escapeSyntaxCallMode", "callIfNoReturn")
        }
        return DriverManager.getConnection(EnvParser.required("SALARY_DB_URL"), properties)
    }
}
