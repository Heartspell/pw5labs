package org.example.pw5labs.cipher

import org.example.pw5labs.common.ErrorWindow
import javafx.fxml.FXML
import javafx.scene.control.TextField

class CipherController {
    @FXML private lateinit var startField1: TextField
    @FXML private lateinit var resultField1: TextField
    @FXML private lateinit var startField2: TextField
    @FXML private lateinit var keyField: TextField
    @FXML private lateinit var resultField2: TextField

    @FXML
    private fun codeWithoutKey() {
        resultField1.text = applyCaesar(startField1.text, FIXED_SHIFT)
    }

    @FXML
    private fun decodeWithoutKey() {
        resultField1.text = applyCaesar(startField1.text, -FIXED_SHIFT)
    }

    @FXML
    private fun codeWithKey() {
        transformWithEnteredKey(1)
    }

    @FXML
    private fun decodeWithKey() {
        transformWithEnteredKey(-1)
    }

    private fun transformWithEnteredKey(direction: Int) {
        val key = keyField.text.toIntOrNull()
        if (key == null) {
            ErrorWindow().show("Caesar Cipher", "Enter an integer in the Key field")
            return
        }
        resultField2.text = applyCaesar(startField2.text, direction * key)
    }

    private fun applyCaesar(text: String, shift: Int): String = buildString(text.length) {
        text.forEach { character ->
            append(shiftCharacter(character, shift))
        }
    }

    private fun shiftCharacter(character: Char, shift: Int): Char {
        val alphabetStart = when {
            character in 'А'..'Я' -> 'А'
            character in 'а'..'я' -> 'а'
            character in 'A'..'Z' -> 'A'
            character in 'a'..'z' -> 'a'
            else -> return character
        }
        val alphabetSize = if (character in 'А'..'я') 32 else 26
        val position = character.code - alphabetStart.code
        val normalizedShift = ((shift % alphabetSize) + alphabetSize) % alphabetSize
        return (alphabetStart.code + (position + normalizedShift) % alphabetSize).toChar()
    }

    private companion object {
        const val FIXED_SHIFT = 3
    }
}
