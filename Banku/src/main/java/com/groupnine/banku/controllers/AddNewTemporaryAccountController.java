package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;
import com.groupnine.banku.businesslogic.*;
import com.groupnine.banku.miscellaneous.InputValidationResult;
import com.groupnine.banku.miscellaneous.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddNewTemporaryAccountController {
    public static WindowContextController activeInstance;

    @FXML
    private TextField accountNameInput;
    @FXML
    private TextField ownerNumberInput;
    @FXML
    private TextField initialBalanceInput;

    @FXML
    private DatePicker datePicker;

    @FXML
    private RadioButton radioButton1;
    @FXML
    private RadioButton radioButton2;


    @FXML
    private Text resultText;
    @FXML
    private Text explainText;

    // todo: extract EMPTY_STR to miscellaneous
    final String EMPTY_STR = "";

    private void clearView() {
        resultText.setText(EMPTY_STR);
        explainText.setText(EMPTY_STR);
        clearInputForms();
    }

    private void clearInputForms() {
        accountNameInput.setText(EMPTY_STR);
        ownerNumberInput.setText(EMPTY_STR);
        initialBalanceInput.setText(EMPTY_STR);
        datePicker.getEditor().clear();
        radioButton1.setSelected(false);
        radioButton2.setSelected(true);
    }

    @FXML
    protected void initialize()
    {
        radioButton1.setOnMouseClicked(e -> {
            radioButton1.setSelected(true);
            radioButton2.setSelected(false);
        });
        radioButton2.setOnMouseClicked(e -> {
            radioButton1.setSelected(false);
            radioButton2.setSelected(true);
        });
        clearView();
    }

    @FXML
    protected void clearButtonOnClick() {
        clearView();
    }

    @FXML
    protected void createButtonOnClick()
    {
        final BankAgency agency = BankAgency.getInstance();

        InputValidationResult result = validateInputs();

        if (result.isValid) {
            final String ownerNumber = ownerNumberInput.getText();
            final String name = accountNameInput.getText();
            final String balance = initialBalanceInput.getText();
            final LocalDate expirationDate = datePicker.getValue();
            final boolean hasBoost = radioButton1.isSelected();

            final AccountOwner owner = agency.findAccountOwnerByID(ownerNumber);

            if (owner instanceof ParticularAccountOwner pOwner) {
                TemporaryParticularAccount account = BankuApp.globalAccFactory.createTemporaryParticularAccount(
                        name, Double.parseDouble(balance), pOwner, expirationDate);

                if (hasBoost) account.activateBoost();

                BankuApp.currentOperator.addNewAccountToTheBank(account);

                clearInputForms();

                if (AccountsController.getActiveInstance() != null) {
                    AccountsController.getActiveInstance().refreshTables();
                }

                result.explainStatus = "A conta '" + name + "' foi adicionada com sucesso.";
            } else {
                result = new InputValidationResult(false, "O tipo de dono de conta fornecido não é de empresa!\n");
            }
        }

        displayResults(result);
    }

    @FXML
    protected void searchOwnerButtonOnClick() {
        SelectOwnerIdController.setAccountTypeFilter(AccountType.PARTICULAR);
        openAccountIdSelectionWindow(selectedId -> ownerNumberInput.setText(selectedId));
    }

    protected void openAccountIdSelectionWindow(OnValidSelectedAction action) {
        SelectOwnerIdController.setOnValidSelectedAction(action);
        if (SelectOwnerIdController.activeWindowInstance == null) {
            SelectOwnerIdController.activeWindowInstance = new WindowContextController(
                    390, 535, "views/select-owner-id-view.fxml", "Select Owner");
            SelectOwnerIdController.activeWindowInstance.showDefaultView();
        } else {
            SelectOwnerIdController.activeWindowInstance.getStage().show();
        }
    }

    @FXML
    protected void cancelButtonOnClick() {
        if (activeInstance != null)
            activeInstance.getStage().close();
    }

    private void displayResults(final InputValidationResult result)
    {
        resultText.setText(result.isValid ? "Sucesso!" : "Insucesso!");
        resultText.setFill(Paint.valueOf(result.isValid ? "green" : "red"));
        explainText.setText(result.explainStatus);
    }

    private InputValidationResult validateInputs()
    /* Valida os inputs e retorna o resultado da validação global. */
    {
        List<InputValidationResult> list = new ArrayList<>();
        list.add(validateAccountName(accountNameInput.getText()));
        list.add(validateOwnerNumber(ownerNumberInput.getText(), "dono"));
        list.add(validateInitialBalance(initialBalanceInput.getText()));
        list.add(validateDatePicked());

        InputValidationResult finalResult = new InputValidationResult(true, "");

        for (InputValidationResult res : list) {
            if (!res.isValid) {
                finalResult.isValid = false;

                finalResult.explainStatus = finalResult.explainStatus.concat(res.explainStatus + "\n");
            }
        }

        if (finalResult.isValid) {
            finalResult.explainStatus = "Conta criada com sucesso!";
        }

        return finalResult;
    }

    private InputValidationResult validateDatePicked() {
        final LocalDate date = datePicker.getValue();

        if (date == null) {
            return new InputValidationResult(false, "Data de expiração não foi escolhida.");
        }
        else if (date.isBefore(LocalDate.now())) {
            return new InputValidationResult(false, "Data deve ser posterior a hoje!");
        } else {
            return new InputValidationResult(true);
        }
    }

    private InputValidationResult validateAccountName(String value)
    {
        final BankAgency agency = BankAgency.getInstance();

        if (value.isEmpty()) {
            return new InputValidationResult(false, "Nome da conta não foi inserida.");
        }
        else if (agency.findAccountByName(value) != null) {
            return new InputValidationResult(false, "Nome da conta já existe na agência.");
        }
        else if (value.length() < 3) {
            return new InputValidationResult(false, "Nome da conta é muito pequeno, requer-se ao menos 3 caracteres.");
        }
        else {
            return new InputValidationResult(true);
        }
    }

    private InputValidationResult validateOwnerNumber(String value, String forForm)
    {
        final BankAgency agency = BankAgency.getInstance();

        if (value.isEmpty()) {
            return new InputValidationResult(false, "Número do " + forForm +  " não foi inserido.");
        }
        else if (agency.findAccountOwnerByID(value) == null) {
            return new InputValidationResult(false, "Número do " + forForm +  " não encontrado na agência.");
        }
        else {
            return new InputValidationResult(true);
        }
    }

    private InputValidationResult validateInitialBalance(String value)
    {
        final double minimumInitialBalance = 5000;
        double initialBalance;

        try {
            initialBalance = Double.parseDouble(value);
        } catch (NullPointerException exception) {
            return new InputValidationResult(false, "Balanço inicial não foi inserido.");
        } catch (NumberFormatException exception) {
            if (value.isEmpty())
                return new InputValidationResult(false, "Balanço inicial não foi inserido.");
            return new InputValidationResult(false, "Valor inválido para balanço inicial. Deve ser um número.");
        }

        if (initialBalance < minimumInitialBalance) {
            return new InputValidationResult(false, "Balanço deve maior ou igual a " + minimumInitialBalance + " escudos.");
        }
        else {
            return new InputValidationResult(true);
        }
    }
}
