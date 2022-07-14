package com.groupnine.banku.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ClientsController {
    @FXML
    private Button dashboardBtn;

    @FXML
    protected void dashboardBtnOnClick() throws IOException {
        WindowsContextController.showDashboardOnPrincipalStage();
    }
}
