package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class OperationsController {
    @FXML
    private VBox vBox1;
    @FXML
    private VBox vBox2;

    @FXML
    protected void dashboardBtnOnClick() {
        BankuApp.getMainWindow().showDefaultView();
    }

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
    protected void onVBox1MouseClick() {
        BankuApp.getMainWindow().showView("views/Transacoes-view.fxml", "Banku - Transações");
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
        //BankuApp.getMainWindow().showView("views/clients-view.fxml", "Banku - Clientes");
        //
    }
}
