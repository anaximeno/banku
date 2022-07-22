package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;
import com.groupnine.banku.businesslogic.Account;
import com.groupnine.banku.businesslogic.BankAgency;
import com.groupnine.banku.businesslogic.IOperator;
import com.groupnine.banku.miscellaneous.Result;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
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
        clearInputs();
        withdrawDate.setText(LocalDate.now().toString());
        depositDate.setText(LocalDate.now().toString());
        transferenceDate.getEditor().setText(LocalDate.now().toString());

        operationsButton.setOnMouseClicked(e -> {
            BankuApp.getMainWindow().showView("views/operations-view.fxml", "Banku - Operations");
        });

        dashboardButton.setOnMouseClicked(e -> {
            BankuApp.getMainWindow().showDefaultView();
        });

        confirmButton.setOnMouseClicked(e -> makeOperation());
        clearButton.setOnMouseClicked(e -> clearInputs());

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

    Result validateWitdhdrawInputs()
    {
        Result finalResult = new Result(false);

        final String account = withdrawAccountInput.getText();
        final String value = withdrawValueInput.getText();

        String prefix = "";

        if (account == null || account.isEmpty()) {
            finalResult.isValid = false;
            finalResult.explainStatus = "Número da conta não foi inserida!";
            prefix = "\n";
        }

        if (value == null || value.isEmpty()) {
            finalResult.isValid = false;
            finalResult.explainStatus += prefix + "Valor não inserido!";
        } else {
            try {
                double val = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                finalResult.isValid = false;
                finalResult.explainStatus += prefix + "Valor deve ser um número!";
            }
        }

        return finalResult;
    }

    Result validateDepositInputs()
    {
        Result finalResult = new Result(true);

        final String account = depositAccountInput.getText();
        final String value = depositValueInput.getText();

        String prefix = "";

        if (account == null || account.isEmpty()) {
            finalResult.isValid = false;
            finalResult.explainStatus = "Número da conta não foi inserida!";
            prefix = "\n";
        }

        if (value == null || value.isEmpty()) {
            finalResult.isValid = false;
            finalResult.explainStatus += prefix + "Valor não inserido!";
        } else {
            try {
                double val = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                finalResult.isValid = false;
                finalResult.explainStatus += prefix + "Valor deve ser um número!";
            }
        }

        return finalResult;
    }

    Result validateTransferenceInputs()
    {
        Result finalResult = new Result(false);
        // todo
        return finalResult;
    }

    private void displayResults(final Result result)
    {
        resultText.setText(result.isValid ? "Sucesso!" : "Insucesso!");
        resultText.setFill(Paint.valueOf(result.isValid ? "green" : "red"));
        explainText.setText(result.explainStatus);
    }

    void makeMoneyWithdraw() {
        Result res = validateWitdhdrawInputs();
        if (!res.isValid) {
            displayResults(res);
        }
    }

    void makeDeposit() {
        BankAgency agency = BankAgency.getInstance();
        Result result = validateDepositInputs();

        if (!result.isValid) {
            displayResults(result);
            return;
        }

        final Account account = agency.findAccountByNumber(depositAccountInput.getText());
        final double value = Double.parseDouble(depositValueInput.getText());

        if (account == null) {
            result.isValid = false;
            result.explainStatus = "Conta " + depositAccountInput.getText() + " não foi encontrada!";
            displayResults(result);
            return;
        }

        boolean execRes = BankuApp.currentOperator.makeMoneyDeposit(account, value);

        if (execRes) {
            result.isValid = true;
            result.explainStatus = "Depósito efetuado com sucesso!";
        } else {
            result.isValid = false;
            result.explainStatus = "O depósito não foi efetuado!";
        }

        displayResults(result);
    }

    void makeTransference() {

    }

    void makeOperation() {
        makeDeposit();
    }

    void confirmButtonOnClick() {

    }
}
