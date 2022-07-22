package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class TransactionsController {

    @FXML
    private Text resultText;
    @FXML
    private Text explainText;

    @FXML
    private TextField withdrawAccountInput;
    @FXML
    private TextField withdrawValueInput;
    @FXML
    private TextField withdrawDate;
    @FXML
    private TextField depositAccountInput;
    @FXML
    private TextField depositValueInput;
    @FXML
    private TextField depositDate;
    @FXML
    private TextField transferenceDebitedAccountInput;
    @FXML
    private TextField transferenceCreditedAccountInput;
    @FXML
    private TextField transferenceValueInput;
    @FXML
    private DatePicker transferenceDate;
    @FXML
    private TextArea transferenceClientDescriptionTextArea;

    @FXML
    private Button dashboardButton;
    @FXML
    private Button operationsButton;

    @FXML
    private Button confirmButton;
    @FXML
    private Button clearButton;

    @FXML
    private Tab tabDeposit;
    @FXML
    private Tab tabWithdraw;
    @FXML
    private Tab tabTransference;

    @FXML
    void initialize()
    {
        withdrawDate.setText(LocalDate.now().toString());
        depositDate.setText(LocalDate.now().toString());
        transferenceDate.getEditor().setText(LocalDate.now().toString());

        operationsButton.setOnMouseClicked(e -> {
            BankuApp.getMainWindow().showView("views/operations-view.fxml", "Banku - Operations");
        });

        dashboardButton.setOnMouseClicked(e -> {
            BankuApp.getMainWindow().showDefaultView();
        });
    }

}
