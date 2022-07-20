package com.groupnine.banku.controllers;

import com.groupnine.banku.businesslogic.AccountOwner;
import com.groupnine.banku.businesslogic.BankAgency;
import com.groupnine.banku.businesslogic.EnterpriseAccountOwner;
import com.groupnine.banku.businesslogic.ParticularAccountOwner;
import com.groupnine.banku.miscellaneous.LogType;
import com.groupnine.banku.miscellaneous.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class SelectOwnerIdController {
    public static WindowContextController activeIntance;

    @FXML
    ListView<String> listView;

    @FXML
    Button selectButton;

    @FXML
    void initialize() {
        selectButton.setDisable(true);
        listView.setOnMouseClicked(windowEvent -> {if (getSelectedItem() != null) selectButton.setDisable(false);});
        for (AccountOwner owner : BankAgency.getInstance().getClients()) {
            if (owner instanceof ParticularAccountOwner) { // todo: make for enterprise also
                listView.getItems().add(formatted(owner));
            }
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

    String getSelectedItem() {
        return listView.selectionModelProperty().get().getSelectedItem();
    }

    @FXML
    protected void cancelButtonOnClick() {
        if (activeIntance != null)
            activeIntance.getStage().close();
    }

    @FXML
    protected void selectButtonOnClick() {
        String selection = getSelectedItem();

        if (selection != null) {
            String[] split = selection.split(" ");
            String id = split[1];

            try {
                int value = Integer.parseInt(id);
                AddNewParticularAccountController.publicOwnerNumInput.setText(id);
                activeIntance.getStage().close();
            } catch (NumberFormatException exception) {
                Logger.log("Error at parsing id in selectButtonOnClick at SelectOwnerIdController", LogType.ERROR);
                Logger.log(exception.getMessage(), LogType.ERROR);
            }
        }
    }
}
