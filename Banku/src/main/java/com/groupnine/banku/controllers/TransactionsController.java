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
    private Button withdrawSelectAccountButton;
    @FXML
    private Button depositSelectAccountButton;
    @FXML
    private Button transferenceSelectDebitedAccountButton;
    @FXML
    private Button transferenceSelectCreditedAccountButton;

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

    private final String EMPTY_STR = "";

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

    void clearInputs()
    {
        resultText.setText(EMPTY_STR);
        explainText.setText(EMPTY_STR);
        withdrawAccountInput.setText(EMPTY_STR);
        withdrawValueInput.setText(EMPTY_STR);
        depositAccountInput.setText(EMPTY_STR);
        depositValueInput.setText(EMPTY_STR);
        transferenceClientDescriptionTextArea.setText(EMPTY_STR);
        transferenceValueInput.setText(EMPTY_STR);
        transferenceCreditedAccountInput.setText(EMPTY_STR);
        transferenceDebitedAccountInput.setText(EMPTY_STR);
    }

    void validateWitdhdrawInputs()
    {
        // todo
    }

    void validateDepositInputs()
    {
        // todo
    }

    void validateTransferenceInputs()
    {
        // todo
    }

    void confirmButtonOnClick() {

    }
}
