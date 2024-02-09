module com.example.jfxtest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.jfxtest to javafx.fxml;
    exports com.example.jfxtest;
}