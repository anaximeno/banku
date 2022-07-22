package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;
import com.groupnine.banku.businesslogic.AccountOwner;
import com.groupnine.banku.businesslogic.BankAgency;
import com.groupnine.banku.businesslogic.OrdinaryParticularAccount;
import com.groupnine.banku.businesslogic.ParticularAccountOwner;
import com.groupnine.banku.miscellaneous.InputValidationResult;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class AddNewParticularClientController {
    public static WindowContextController activeWindow;

    @FXML
    private TextField nameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField NIFInput;
    @FXML
    private TextField addressInput;
    @FXML
    private TextField nationalityInput;

    @FXML
    private Text resultText;
    @FXML
    private Text explainText;

    @FXML
    private Button clearButton;
    @FXML
    private Button cancelButton;

    final String EMPTY_STR = "";

    private void clearView() {
        resultText.setText(EMPTY_STR);
        explainText.setText(EMPTY_STR);
        clearInputForms();
    }

    private void clearInputForms() {
        nameInput.setText(EMPTY_STR);
        lastNameInput.setText(EMPTY_STR);
        NIFInput.setText(EMPTY_STR);
        addressInput.setText(EMPTY_STR);
        nationalityInput.setText(EMPTY_STR);
    }

    @FXML
    protected void initialize()
    {
        clearButton.setOnMouseClicked(e -> clearView());
        cancelButton.setOnMouseClicked(e -> activeWindow.getStage().close());
        clearView();
    }

    @FXML
    protected void createButtonOnClick() {
        InputValidationResult result = validateInputs();

        if (result.isValid) {
            final String name = nameInput.getText();
            final String lastName = lastNameInput.getText();
            final String NIF = NIFInput.getText();
            final String address = addressInput.getText();
            final String nationality = nationalityInput.getText();

            ParticularAccountOwner client = BankuApp.globalAccOwnFactory.createParticularAccountOwner(
                    name, lastName, NIF, address, nationality);

            BankuApp.currentOperator.addNewClientToTheBank(client);
            clearInputForms();

            if (AccountsController.getActiveInstance() != null) {
                AccountsController.getActiveInstance().refreshTables();
            }

            result.explainStatus = "A conta '" + name + "' foi adicionada com sucesso.";
        }

        displayResults(result);
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
        list.add(validateInputForm(nameInput.getText(), "Name", 3));
        list.add(validateInputForm(lastNameInput.getText(), "Apelido", 3));
        list.add(validateInputForm(NIFInput.getText(), "NIF", 12));
        list.add(validateInputForm(nationalityInput.getText(), "Nacionalidade", 3));

        InputValidationResult finalResult = new InputValidationResult(true, "");

        for (InputValidationResult res : list) {
            if (!res.isValid) {
                finalResult.isValid = false;

                finalResult.explainStatus = finalResult.explainStatus.concat(res.explainStatus + "\n");
            }
        }

        if (finalResult.isValid) {
            finalResult.explainStatus = "Cliente criado com sucesso!";
        }

        return finalResult;
    }

    private InputValidationResult validateInputForm(String input, String formName, int minLength)
    {
        if (input == null || input.isEmpty()) {
            return new InputValidationResult(false, formName + " não foi preenchido!");
        } else if (input.length() < minLength) {
            return new InputValidationResult(false, formName + " deve ter ao menos " + minLength + " caracteres");
        } else {
            return new InputValidationResult(true);
        }
    }
}
