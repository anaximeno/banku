package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;
import com.groupnine.banku.businesslogic.*;
import com.groupnine.banku.miscellaneous.InputValidationResult;
import com.groupnine.banku.miscellaneous.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;

public class AddNewEnterpriseAccountController {
    public static WindowContextController activeInstance;

    @FXML
    private TextField accountNameInput;
    @FXML
    private TextField ownerNumberInput;
    @FXML
    private TextField initialBalanceInput;
    @FXML
    private TextField adminNumberInput;

    @FXML
    private TextFlow authorizedUsersTextFlow;

    @FXML
    private Button addAuthorizedUsersButton;

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
        adminNumberInput.setText(EMPTY_STR);
        authorizedUsersTextFlow.getChildren().clear();
    }

    @FXML
    protected void initialize()
    {
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
            final String adminNumber = adminNumberInput.getText();
            final String name = accountNameInput.getText();
            final String balance = initialBalanceInput.getText();
            final ArrayList<ParticularAccountOwner> authUsers = getAuthorizedUsers();

            final AccountOwner owner = agency.findAccountOwnerByID(ownerNumber); // todo: finish bellow
            final ParticularAccountOwner admin = (ParticularAccountOwner) agency.findAccountOwnerByID(adminNumber);

            if (owner instanceof EnterpriseAccountOwner eOwner) {
                // for parAcc
                EnterpriseAccount account = BankuApp.globalAccFactory.createEnterpriseAccount(
                        name, eOwner, admin, Double.parseDouble(balance), authUsers);

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

    private ArrayList<ParticularAccountOwner> getAuthorizedUsers() {
        BankAgency agency = BankAgency.getInstance();
        ArrayList<ParticularAccountOwner> list = new ArrayList<>();
        for (Node text : authorizedUsersTextFlow.getChildren()) {
            final String id = ((Text) text).getText();
            Logger.log(id);
            final ParticularAccountOwner accountOwner = (ParticularAccountOwner) agency.findAccountOwnerByID(id);
            // todo: check for repeated accNum
            list.add(accountOwner);
        }
        return list;
    }

    @FXML
    protected void addAuthorizedUsersButtonOnClick() {
        SelectOwnerIdController.setAccountTypeFilter(AccountType.PARTICULAR);
        openAccountIdSelectionWindow(selectedId -> authorizedUsersTextFlow.getChildren().add(new Text(selectedId + "\n")));
    }

    @FXML
    protected void searchOwnerButtonOnClick() {
        SelectOwnerIdController.setAccountTypeFilter(AccountType.ENTERPRISE);
        openAccountIdSelectionWindow(selectedId -> ownerNumberInput.setText(selectedId));
    }

    @FXML
    protected void searchAdminButtonOnClick() {
        SelectOwnerIdController.setAccountTypeFilter(AccountType.PARTICULAR);
        openAccountIdSelectionWindow(selectedId -> adminNumberInput.setText(selectedId));
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
        list.add(validateOwnerNumber(adminNumberInput.getText(), "admin"));

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
