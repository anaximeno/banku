package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;
import com.groupnine.banku.businesslogic.EnterpriseAccountOwner;
import com.groupnine.banku.businesslogic.EnterprisePartner;
import com.groupnine.banku.miscellaneous.Result;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;

public class AddNewEnterpriseClientController {
    public static WindowContextController activeWindow;

    @FXML
    private TextField nameInput;
    @FXML
    private TextField NIFInput;
    @FXML
    private TextField addressInput;
    @FXML
    private TextField partnersNameInput;
    @FXML
    private TextField partnersCNIInput;

    @FXML
    private Text resultText;
    @FXML
    private Text explainText;

    @FXML
    private Button clearButton;
    @FXML
    private Button cancelButton;

    @FXML
    private TextFlow partnersTextFlow;

    final String EMPTY_STR = "";

    private void clearView() {
        resultText.setText(EMPTY_STR);
        explainText.setText(EMPTY_STR);
        clearInputForms();
    }

    private void clearInputForms()
    {
        nameInput.setText(EMPTY_STR);
        NIFInput.setText(EMPTY_STR);
        addressInput.setText(EMPTY_STR);
        partnersCNIInput.setText(EMPTY_STR);
        partnersNameInput.setText(EMPTY_STR);
        partnersTextFlow.getChildren().clear();
    }

    @FXML
    protected void initialize()
    {
        clearButton.setOnMouseClicked(e -> clearView());
        cancelButton.setOnMouseClicked(e -> activeWindow.getStage().close());
        clearView();
    }

    protected ArrayList<EnterprisePartner> getPartners()
    {
        ArrayList<EnterprisePartner> list = new ArrayList<>();

        for (Node node : partnersTextFlow.getChildren()) {
            final Text text = (Text) node;
            final String[] split = text.getText().split(" ");
            list.add(new EnterprisePartner(split[0], split[1]));
        }

        return list;
    }

    @FXML
    protected void createButtonOnClick()
    {
        Result result = validateInputs();

        if (result.isValid) {
            final String name = nameInput.getText();
            final String NIF = NIFInput.getText();
            final String address = addressInput.getText();
            final ArrayList<EnterprisePartner> partners = getPartners();

            EnterpriseAccountOwner client = BankuApp.globalAccOwnFactory.createEnterpriseAccountOwner(name, NIF, address, partners);

            BankuApp.currentOperator.addNewClientToTheBank(client);
            clearInputForms();

            if (AccountsController.getActiveInstance() != null) {
                AccountsController.getActiveInstance().refreshTables();
            }

            result.explainStatus = "A conta '" + name + "' foi adicionada com sucesso.";
        }

        displayResults(result);
    }

    @FXML
    protected void addPartnerButtonOnClick()
    {
        boolean saveValue = true;
        final String partnerName = partnersNameInput.getText();
        final String partnerCNI = partnersCNIInput.getText();

        if (partnerName == null || partnerName.isEmpty()) {
            explainText.setText("O nome do parceiro não foi inserido!");
            saveValue = false;
        }
        if (partnerCNI == null || partnerCNI.isEmpty()) {
            explainText.setText(explainText.getText() + "\nO CNI do parceiro não foi inserido!");
            saveValue = false;
        }

        if (!saveValue)
            return;

        partnersTextFlow.getChildren().add(new Text(partnerName + " " + partnerCNI));
        explainText.setText(EMPTY_STR);
    }

    private void displayResults(final Result result)
    {
        resultText.setText(result.isValid ? "Sucesso!" : "Insucesso!");
        resultText.setFill(Paint.valueOf(result.isValid ? "green" : "red"));
        explainText.setText(result.explainStatus);
    }

    private Result validateInputs()
        /* Valida os inputs e retorna o resultado da validação global. */
    {
        List<Result> list = new ArrayList<>();
        list.add(validateInputForm(nameInput.getText(), "Name", 3, false));
        list.add(validateInputForm(NIFInput.getText(), "NIF", 12, false));
        list.add(validateInputForm(addressInput.getText(), "Endereço", 5, false));

        Result finalResult = new Result(true, "");

        for (Result res : list) {
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

    private Result validateInputForm(String input, String formName, int minLength, boolean canBeEmpty)
    {
        if (canBeEmpty && (input == null || input.isEmpty())) {
            return new Result(false, formName + " não foi preenchido!");
        } else if (input.length() < minLength) {
            return new Result(false, formName + " deve ter ao menos " + minLength + " caracteres");
        } else {
            return new Result(true);
        }
    }
}