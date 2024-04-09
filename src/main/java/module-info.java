module com.example.jfxtest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.github.oshi;
    requires java.management;


    opens com.example.jfxtest to javafx.fxml;
    exports com.example.jfxtest;
}