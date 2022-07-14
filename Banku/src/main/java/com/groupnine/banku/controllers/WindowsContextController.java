package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowsContextController {
    private static Stage principalStage;
    private static double defaultHeight = 600;
    private static double defaultWidth = 840;
    private static String defaultTitle = "Banku - A Banking Application";

    public static void setTitle(String title) {
        if (principalStage != null) {
            principalStage.setTitle(title);
        }
    }

    public static void setPrincipalScene(Scene scene) {
        getPrincipalStage().setScene(scene);
    }

    public static void setPrincipalStage(Stage stage, Scene mainScene) {
        assert stage != null; // stage should not be null
        principalStage = stage;
        principalStage.setTitle(defaultTitle);
        principalStage.setHeight(defaultHeight);
        principalStage.setWidth(defaultWidth);
        principalStage.setResizable(false);
        setPrincipalScene(mainScene);
        setTitle(defaultTitle);
    }

    public static void setPrincipalStage(Stage stage) {
        setPrincipalStage(stage, new Scene(new Group(), defaultHeight, defaultWidth));
    }

    public static Stage getPrincipalStage() {
        if (principalStage == null) {
            setPrincipalStage(new Stage());
        }
        return principalStage;
    }

    public static void openSceneGraphViewOnPrincipalStage(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankuApp.class.getResource(path));
        getPrincipalStage().getScene().setRoot(fxmlLoader.load());
        getPrincipalStage().show();
    }

    public static void showDashboardOnPrincipalStage() throws IOException {
        openSceneGraphViewOnPrincipalStage("views/dashboard-view.fxml");
        setTitle("Banku - Dashboard");
    }
}
