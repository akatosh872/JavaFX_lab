package com.example.jfxtest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageController extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StageController.class.getResource("main_panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 640);
        stage.setTitle("System Information");
        stage.setMinHeight(480);
        stage.setMinWidth(640);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}