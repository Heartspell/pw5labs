package org.example.pw5labs.launcher

import org.example.pw5labs.common.ErrorWindow
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class LauncherController {
    @FXML
    private fun openSalaryCalculator() {
        openWindow("/org/example/pw5labs/salary/salaries.fxml", "Расчёт заработной платы", 1280.0, 760.0)
    }

    @FXML
    private fun openCipher() {
        openWindow("/org/example/pw5labs/cipher/cipher.fxml", "Шифрование текста", 760.0, 620.0)
    }

    @FXML
    private fun openBarcode() {
        openWindow("/org/example/pw5labs/qrcode/qrcode.fxml", "Генератор штрих-кода", 780.0, 650.0)
    }

    private fun openWindow(resource: String, title: String, width: Double, height: Double) {
        try {
            val loader = FXMLLoader(LauncherController::class.java.getResource(resource))
            Stage().apply {
                this.title = title
                scene = Scene(loader.load(), width, height)
                show()
            }
        } catch (error: Exception) {
            ErrorWindow().show("Запуск приложения", error.message ?: "Не удалось открыть окно")
        }
    }
}
