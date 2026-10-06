package org.example.pw5labs.common

import javafx.scene.control.Alert
import javafx.scene.control.ButtonType

class ConfirmationWindow {
    fun show(title: String, message: String): Boolean =
        Alert(Alert.AlertType.CONFIRMATION).apply {
            this.title = title
            contentText = message
        }.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK
}
