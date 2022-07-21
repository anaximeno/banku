package com.groupnine.banku.controllers;

import com.groupnine.banku.businesslogic.AccountOwner;
import com.groupnine.banku.businesslogic.AccountType;
import com.groupnine.banku.businesslogic.BankAgency;
import com.groupnine.banku.businesslogic.ParticularAccountOwner;
import com.groupnine.banku.miscellaneous.LogType;
import com.groupnine.banku.miscellaneous.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.List;


public class SelectOwnerIdController {
    public static WindowContextController activeWindowInstance;
    public static OnValidSelectedAction onValidSelectedAction;
    public static AccountType accountTypeFilter;

    @FXML
    ListView<String> listView;

    @FXML
    Button selectButton;

    @FXML
    void initialize() {
        final BankAgency agency = BankAgency.getInstance();

        selectButton.setDisable(true);
        listView.setOnMouseClicked(windowEvent -> {if (getSelectedItem() != null) selectButton.setDisable(false);});

        if (accountTypeFilter != null) {
            switch (accountTypeFilter) {
                case PARTICULAR, TEMPORARY -> addItemsToTheListView(agency.getParticularClientList());
                case ENTERPRISE -> addItemsToTheListView(agency.getEnterpriseClientList());
            }
        } else {
            addItemsToTheListView(agency.getClients());
        }
    }

    private<T extends AccountOwner> void addItemsToTheListView(final List<T> list)
    {
        for (T accOwn : list) {
            listView.getItems().add(formatted(accOwn));
        }
    }

    private String formatted(AccountOwner owner) {
        String ID, fullN, NIF;
        ID = owner.getOwnerID();
        if (owner instanceof ParticularAccountOwner pAccOwn) {
            fullN = pAccOwn.getFullName();
        } else {
            fullN = owner.getName();
        }
        NIF = owner.getNIF();

        return "ID: " + ID + " Name: " + fullN + " NIF: " + NIF;
    }

    public static void setOnValidSelectedAction(OnValidSelectedAction action)
    {
        onValidSelectedAction = action;
    }

    public static void setAccountTypeFilter(AccountType accountType)
    {
        accountTypeFilter = accountType;
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
