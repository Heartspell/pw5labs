module org.example.pw5labs {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.desktop;
    requires javafx.web;
    requires java.sql;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens org.example.pw5labs to javafx.fxml;
    opens org.example.pw5labs.common to javafx.fxml;
    opens org.example.pw5labs.launcher to javafx.fxml;
    opens org.example.pw5labs.salary to javafx.fxml;
    opens org.example.pw5labs.cipher to javafx.fxml;
    opens org.example.pw5labs.qrcode to javafx.fxml;
    exports org.example.pw5labs;
    exports org.example.pw5labs.common;
    exports org.example.pw5labs.launcher;
    exports org.example.pw5labs.salary;
    exports org.example.pw5labs.cipher;
    exports org.example.pw5labs.qrcode;
}
