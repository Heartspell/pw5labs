package org.example.pw5labs

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class MainApplication : Application() {
    override fun start(stage: Stage) {
        val loader = FXMLLoader(MainApplication::class.java.getResource("salaries.fxml"))
        stage.title = "Лабораторная работа №1"
        stage.scene = Scene(loader.load(), 1280.0, 760.0)
        stage.show()
    }

}
