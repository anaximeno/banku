package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;
import com.groupnine.banku.businesslogic.Account;
import com.groupnine.banku.businesslogic.BankAgency;
import com.groupnine.banku.miscellaneous.Logger;
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

    Result validateAccountInput(String account)
    {
        if (account == null || account.isEmpty()) {
            return new Result(false, "Número da conta não foi inserida!");
        } else {
            return new Result(true);
        }
    }

    Result validateAccountInput(String account, String tipoDeConta)
    {
        if (account == null || account.isEmpty()) {
            return new Result(false, "Número da conta " + tipoDeConta + " não foi inserida!");
        } else {
            return new Result(true);
        }
    }

    Result validateValue(String value)
    {
        Result finalResult = new Result(true);

        if (value == null || value.isEmpty()) {
            finalResult.isValid = false;
            finalResult.explainStatus = "Valor não inserido!";
        } else {
            try {
                double val = Double.parseDouble(value);

                if (val <= 0) {
                    finalResult.isValid = false;
                    finalResult.explainStatus = "Valor deve ser maior que zero!";
                }
            } catch (NumberFormatException e) {
                finalResult.isValid = false;
                finalResult.explainStatus = "Valor deve ser um número!";
            }
        }

        return finalResult;
    }

    Result validateWitdhdrawInputs()
    {
        Result res1 = validateAccountInput(withdrawAccountInput.getText());
        Result res2 = validateValue(withdrawValueInput.getText());

        if (!res1.isValid && !res2.isValid) {
            res1.explainStatus += "\n" + res2.explainStatus;
            return res1;
        } else if (!res1.isValid) {
            return res1;
        } else {
            return res2;
        }
    }

    Result validateDepositInputs()
    {
        Result res1 = validateAccountInput(depositAccountInput.getText());
        Result res2 = validateValue(depositValueInput.getText());

        if (!res1.isValid && !res2.isValid) {
            res1.explainStatus += "\n" + res2.explainStatus;
            return res1;
        } else if (!res1.isValid) {
            return res1;
        } else {
            return res2;
        }
    }

    Result validateTransferenceInputs()
    {
        Result res1 = validateAccountInput(transferenceDebitedAccountInput.getText(), "debitada");
        Result res2 = validateValue(transferenceValueInput.getText());
        Result res3 = validateAccountInput(transferenceCreditedAccountInput.getText(), "creditada");

        if (!res1.isValid && !res2.isValid && !res3.isValid) {
            res1.explainStatus += "\n" + res2.explainStatus + "\n" + res3.explainStatus;
            return res1;
        } else if (!res1.isValid && !res2.isValid) {
            res1.explainStatus += "\n" + res2.explainStatus;
            return res1;
        } else if (!res1.isValid) {
            return res1;
        } else {
            return res2;
        }
    }

    private void displayResults(final Result result)
    {
        resultText.setText(result.isValid ? "Sucesso!" : "Insucesso!");
        resultText.setFill(Paint.valueOf(result.isValid ? "green" : "red"));
        explainText.setText(result.explainStatus);
    }

    void makeMoneyWithdraw() {
        BankAgency agency = BankAgency.getInstance();
        Result result = validateWitdhdrawInputs();

        if (!result.isValid) {
            displayResults(result);
            return;
        }

        final Account account = agency.findAccountByNumber(withdrawAccountInput.getText());
        final double value = Double.parseDouble(withdrawValueInput.getText());

        if (account == null) {
            result.isValid = false;
            result.explainStatus = "Conta " + withdrawAccountInput.getText() + " não foi encontrada!";
            displayResults(result);
            return;
        }

        boolean execRes = BankuApp.currentOperator.makeMoneyWithdraw(account, value);

        if (execRes) {
            result.isValid = true;
            result.explainStatus = "Levantamento efetuado com sucesso!";
        } else {
            result.isValid = false;
            result.explainStatus = "O leventamento não foi efetuado!";
        }

        displayResults(result);
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
        BankAgency agency = BankAgency.getInstance();
        Result result = validateTransferenceInputs();

        if (!result.isValid) {
            displayResults(result);
            return;
        }

        final Account accountDebited = agency.findAccountByNumber(transferenceDebitedAccountInput.getText());
        final Account accountCredited = agency.findAccountByNumber(transferenceCreditedAccountInput.getText());
        final double value = Double.parseDouble(transferenceValueInput.getText());
        final String description = transferenceClientDescriptionTextArea.getText();

        if (accountDebited == null) {
            result.isValid = false;
            result.explainStatus = "Conta " + transferenceDebitedAccountInput.getText() + " não foi encontrada!";
            displayResults(result);
            return;
        }

        if (accountCredited == null) {
            result.isValid = false;
            result.explainStatus = "Conta " + transferenceCreditedAccountInput.getText() + " não foi encontrada!";
            displayResults(result);
            return;
        }

        boolean execRes = BankuApp.currentOperator.makeTransaction(
                accountDebited, accountCredited, value, description);

        if (execRes) {
            result.isValid = true;
            result.explainStatus = "A trasferência efetuada com sucesso!";
        } else {
            result.isValid = false;
            result.explainStatus = "A trasferência não foi efetuado!";
        }

        displayResults(result);
    }

    void makeOperation() {
        if (tabDeposit.isSelected()) {
            makeDeposit();
        } else if (tabWithdraw.isSelected()) {
            makeMoneyWithdraw();
        } else if (tabTransference.isSelected()) {
            makeTransference();
        } else {
            Logger.log("Unknown state at makeOperation in TransactionsController");
        }
    }

    void confirmButtonOnClick() {

    }
}
