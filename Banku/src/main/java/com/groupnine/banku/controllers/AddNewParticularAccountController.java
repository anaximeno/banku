package com.groupnine.banku.controllers;

import com.groupnine.banku.businesslogic.AccountFactory;
import com.groupnine.banku.businesslogic.BankAgency;
import com.groupnine.banku.businesslogic.OrdinaryParticularAccount;
import com.groupnine.banku.businesslogic.ParticularAccountOwner;
import com.groupnine.banku.miscellaneous.InputValidationResult;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
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
    private Label resultLabel;
    @FXML
    private TextFlow explainResultTextFlow;

    @FXML
    protected void initialize()
    {
        resultLabel.setText("");
    }

    @FXML
    protected void createButtonOnClick()
    {
        final BankAgency agency = BankAgency.getInstance();

        final String name = accountNameInput.getText();
        final String ownerNum = ownerNumberInput.getText();
        final String balance = initialBalanceInput.getText();

        final InputValidationResult result = validateInputs(name, ownerNum, balance);

        if (result.isValid) {
            ParticularAccountOwner owner = (ParticularAccountOwner) agency.findAccountOwnerByID(ownerNum);
            OrdinaryParticularAccount acc = AccountFactory.createOrdinaryParticularAccount(name, owner, Double.parseDouble(balance), null);
            agency.addNewAccount(acc);
        }

        displayResults(result);
    }

    private void displayResults(final InputValidationResult result)
    {
        resultLabel.setText(result.isValid ? "Sucesso!" : "Erro!");
        resultLabel.setTextFill(Paint.valueOf( result.isValid ? "red" : "green"));
        explainResultTextFlow.getChildren().add(new Text(result.explainStatus));
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
                finalResult.explainStatus = finalResult.explainStatus.concat(res.explainStatus);
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
        final BankAgency agency = BankAgency.getInstance();
        double initialBalance;

        try {
            initialBalance = Double.parseDouble(value);
        } catch (NullPointerException exception) {
            return new InputValidationResult(false, "Balanço inicial não foi inserido.");
        } catch (NumberFormatException exception) {
            return new InputValidationResult(false, "Valor inválido para balanço inicial. Deve ser um número.");
        }

        if (initialBalance < minimumInitialBalance) {
            return new InputValidationResult(false, "Balanço deve menor ou igual a " + minimumInitialBalance + " escudos.");
        }
        else {
            return new InputValidationResult(true);
        }
    }
}
