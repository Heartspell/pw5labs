package org.example.pw5labs

import javafx.scene.control.Alert

class AlertWindow {
    fun show(title: String, message: String) {
        Alert(Alert.AlertType.WARNING).apply {
            this.title = title
            contentText = message
        }.showAndWait()
    }
}
