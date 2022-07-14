package com.groupnine.banku;

import com.groupnine.banku.controllers.WindowsContextController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BankuApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        WindowsContextController.setPrincipalStage(stage);
        WindowsContextController.showDashboardOnPrincipalStage();
    }

    public static void main(String[] args) {
        launch();
    }
}