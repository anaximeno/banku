package com.groupnine.banku.controllers;

import com.groupnine.banku.businesslogic.*;
import com.groupnine.banku.miscellaneous.LogType;
import com.groupnine.banku.miscellaneous.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.List;


public class SelectAccountIdController {
    public static WindowContextController activeWindowInstance;
    public static OnValidSelectedAction onValidSelectedAction;

    @FXML
    ListView<String> listView;

    @FXML
    Button selectButton;

    @FXML
    void initialize() {
        selectButton.setDisable(true);
        listView.setOnMouseClicked(windowEvent -> {if (getSelectedItem() != null) selectButton.setDisable(false);});
        activeWindowInstance.getStage().setOnShowing(e -> updateListView());
        updateListView();
    }

    private void updateListView() {
        listView.getItems().clear();
        final BankAgency agency = BankAgency.getInstance();
        addItemsToTheListView(agency.getClientAccounts());
    }

    private<T extends Account> void addItemsToTheListView(final List<T> list)
    {
        for (T accOwn : list) {
            listView.getItems().add(formatted(accOwn));
        }
    }

    private String formatted(Account account) {
        String ID, name, type;

        ID = account.getAccountNumber();
        name = account.getAccountName();

        if (account instanceof OrdinaryParticularAccount) {
            type = "Particular";
        } else if (account instanceof TemporaryParticularAccount) {
            type = "Temporary";
        } else {
            type = "Enterprise";
        }

        return "ID: " + ID + " Name: " + name + " TYPE: " + type;
    }

    public static void setOnValidSelectedAction(OnValidSelectedAction action)
    {
        onValidSelectedAction = action;
    }

    String getSelectedItem() {
        return listView.selectionModelProperty().get().getSelectedItem();
    }

    @FXML
    protected void cancelButtonOnClick() {
        if (activeWindowInstance != null)
            activeWindowInstance.getStage().close();
    }

    @FXML
    protected void selectButtonOnClick() {
        String selection = getSelectedItem();

        if (selection != null) {
            String[] split = selection.split(" ");
            String id = split[1];

            try {
                int value = Integer.parseInt(id);
                if (onValidSelectedAction != null) {
                    onValidSelectedAction.action(id);
                }
                activeWindowInstance.getStage().close();
            } catch (NumberFormatException exception) {
                Logger.log("Error at parsing id in selectButtonOnClick at SelectOwnerIdController", LogType.ERROR);
                Logger.log(exception.getMessage(), LogType.ERROR);
            }
        }
    }
}
