package com.groupnine.banku.controllers;

import com.groupnine.banku.businesslogic.AccountOwner;
import com.groupnine.banku.businesslogic.BankAgency;
import com.groupnine.banku.businesslogic.EnterpriseAccountOwner;
import com.groupnine.banku.businesslogic.ParticularAccountOwner;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class SelectOwnerIdController {
    @FXML
    ListView<String> listView;

    @FXML
    void initialize() {
        for (AccountOwner owner : BankAgency.getInstance().getClients()) {
            listView.getItems().add(formatted(owner));
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
}
