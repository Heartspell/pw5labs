package org.example.pw5labs.common

import javafx.scene.control.Alert

class ErrorWindow {
    fun show(title: String, message: String) {
        Alert(Alert.AlertType.ERROR).apply {
            this.title = title
            contentText = message
        }.showAndWait()
    }
}
