package com.groupnine.banku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BankuApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankuApp.class.getResource("views/dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 840, 560);
        stage.setTitle("Banku - A Banking Application");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}