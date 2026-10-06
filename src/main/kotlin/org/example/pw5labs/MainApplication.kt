package org.example.pw5labs

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class MainApplication : Application() {
    override fun start(stage: Stage) {
        val loader = FXMLLoader(MainApplication::class.java.getResource("/org/example/pw5labs/launcher/launcher.fxml"))
        stage.title = "Учебные приложения"
        stage.scene = Scene(loader.load(), 520.0, 360.0)
        stage.show()
    }

}
