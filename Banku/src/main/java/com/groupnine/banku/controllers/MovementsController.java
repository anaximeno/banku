package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;
import com.groupnine.banku.businesslogic.BankAgency;
import com.groupnine.banku.businesslogic.BankingOperation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class MovementsController {
    @FXML
    private Button dashboardButton;
    @FXML
    private Button operationsButton;
    @FXML
    private Button detailsButton;
    @FXML
    private Button refreshButton;

    @FXML
    private Text bankBalanceText;

    @FXML
    private DatePicker initDatePicker;
    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ListView<String> operationsListView;

    @FXML
    void initialize() {
        refreshMovements();

        dashboardButton.setOnMouseClicked(e -> BankuApp.getMainWindow().showDefaultView());

        operationsButton.setOnMouseClicked(e -> {
            BankuApp.getMainWindow().showView("views/operations-view.fxml", "Banku - Operations");
        });

        refreshButton.setOnMouseClicked(e -> refreshMovements());
    }

    void refreshMovements() {
        final BankAgency agency = BankAgency.getInstance();

        operationsListView.getItems().clear();

        for (BankingOperation operation : agency.getOperationsLog()) {
            operationsListView.getItems().add(operation.getDescription());
        }
    }
}
