package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {
    @FXML
    private VBox vBox1;
    @FXML
    private VBox vBox2;
    @FXML
    private VBox vBox3;

    private void setBackgroundColor(VBox vBox, Color color) {
        BackgroundFill backgroundFill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
        vBox.setBackground(new Background(backgroundFill));
    }

    @FXML
    protected void onVBox1MouseIn() {
        setBackgroundColor(vBox1, Color.color(0.9, 0.9, 0.9));
    }

    @FXML
    protected void onVBox1MouseOut() {
        setBackgroundColor(vBox1, Color.color(1, 1, 1));
    }

    @FXML
    protected void onVBox1MouseClick() throws IOException {
        WindowsContextController.openSceneGraphViewOnPrincipalStage("views/accounts-view.fxml");
        WindowsContextController.setTitle("Banku - Contas");
    }

    @FXML
    protected void onVBox2MouseIn() {
        setBackgroundColor(vBox2, Color.color(0.9, 0.9, 0.9));
    }

    @FXML
    protected void onVBox2MouseOut() {
        setBackgroundColor(vBox2, Color.color(1, 1, 1));
    }

    @FXML
    protected void onVBox2MouseClick() throws IOException {
        WindowsContextController.openSceneGraphViewOnPrincipalStage("views/clients-view.fxml");
        WindowsContextController.setTitle("Banku - Clientes");
    }

    @FXML
    protected void onVBox3MouseIn() {
        setBackgroundColor(vBox3, Color.color(0.9, 0.9, 0.9));
    }

    @FXML
    protected void onVBox3MouseOut() {
        setBackgroundColor(vBox3, Color.color(1, 1, 1));
    }

    @FXML
    protected void onVBox3MouseClick() throws IOException {
        WindowsContextController.openSceneGraphViewOnPrincipalStage("views/agency-view.fxml");
        WindowsContextController.setTitle("Banku - Agência");
    }
}
