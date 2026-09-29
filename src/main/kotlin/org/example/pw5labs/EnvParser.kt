package org.example.pw5labs

import java.io.File

object EnvParser {
    private val values: Map<String, String> by lazy { readFile() }

    fun required(name: String): String =
        System.getenv(name)
            ?: values[name]
            ?: error("")

    private fun readFile(): Map<String, String> {
        val file = File(".env")
        if (!file.exists()) return emptyMap()

        return file.readLines()
            .mapNotNull { parseLine(it) }
            .toMap()
    }

    private fun parseLine(line: String): Pair<String, String>? {
        val value = line.trim()
        if (value.isEmpty() || value.startsWith("#")) return null

        val separator = value.indexOf('=')
        if (separator <= 0) return null

        val name = value.substring(0, separator).trim()
        val content = value.substring(separator + 1).trim()
            .removeSurrounding("\"")
            .removeSurrounding("'")

        return name to content
    }
}
