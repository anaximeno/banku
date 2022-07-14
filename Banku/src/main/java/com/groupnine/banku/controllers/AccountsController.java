package com.groupnine.banku.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.io.IOException;

public class AccountsController {
    @FXML
    private Button dashboardBtn;
    @FXML
    private TableView particularAccountsTable;

    @FXML
    protected void dashboardBtnOnClick() throws IOException {
        WindowsContextController.showDashboardOnPrincipalStage();
    }
}
