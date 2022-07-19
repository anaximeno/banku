package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;
import com.groupnine.banku.businesslogic.*;
import com.groupnine.banku.miscellaneous.InputValidationResult;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;

public class AddNewParticularAccountController {
    @FXML
    private TextArea accountNameInput;
    @FXML
    private TextArea ownerNumberInput;
    @FXML
    private TextArea initialBalanceInput;

    @FXML
    private Text resultText;
    @FXML
    private Text explainText;

    @FXML
    private Button searchOwnerButton;

    @FXML
    protected void initialize()
    {
        resultText.setText("");
        explainText.setText("");
        // não foi implementado ainda, então, desativa-se.
        searchOwnerButton.setDisable(true);
    }

    @FXML
    protected void createButtonOnClick()
    {
        final BankAgency agency = BankAgency.getInstance();

        final String name = accountNameInput.getText();
        final String ownerNum = ownerNumberInput.getText();
        final String balance = initialBalanceInput.getText();

        InputValidationResult result = validateInputs(name, ownerNum, balance);

        if (result.isValid) {
            AccountOwner owner = agency.findAccountOwnerByID(ownerNum);

            if (owner instanceof ParticularAccountOwner pOwner) {
                // for parAcc
                OrdinaryParticularAccount account = BankuApp.globalAccFactory.createOrdinaryParticularAccount(
                        name, (ParticularAccountOwner) owner, Double.parseDouble(balance), null
                );

                BankuApp.currentOperator.addNewAccountToTheBank(account);

                if (AccountsController.getActiveInstance() != null) {
                    AccountsController.getActiveInstance().refreshTables();
                }
            } else {
                result = new InputValidationResult(false, "O tipo de utilizador fornecido não é particular!\n");
            }
        }

        displayResults(result);
    }

    @FXML
    protected void searchOwnerButtonOnClick() {
        // todo: implement
    }

    @FXML
    protected void cancelButtonOnClick() {
        // todo: implement
    }

    private void displayResults(final InputValidationResult result)
    {
        resultText.setText(result.isValid ? "Sucesso!" : "Erro!");
        resultText.setFill(Paint.valueOf(result.isValid ? "green" : "red"));
        explainText.setText(result.explainStatus);
    }

    private InputValidationResult validateInputs(String accountName, String accountOwnerNumber, String initialBalance)
    /* Valida os inputs e retorna o resultado da validação global. */
    {
        List<InputValidationResult> list = new ArrayList<>();
        list.add(validateAccountName(accountName));
        list.add(validateOwnerNumber(accountOwnerNumber));
        list.add(validateInitialBalance(initialBalance));

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

    private InputValidationResult validateOwnerNumber(String value)
    {
        final BankAgency agency = BankAgency.getInstance();

        if (value.isEmpty()) {
            return new InputValidationResult(false, "Número do dono não foi inserido.");
        }
        else if (agency.findAccountOwnerByID(value) == null) {
            return new InputValidationResult(false, "Número do dono não encontrado na agência.");
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
