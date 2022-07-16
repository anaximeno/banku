package com.groupnine.banku;

import com.groupnine.banku.businesslogic.*;
import com.groupnine.banku.controllers.WindowsContextController;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class BankuApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        initData();
        WindowsContextController.setPrincipalStage(stage);
        WindowsContextController.showDashboardOnPrincipalStage();
    }

    public static void initData() {
        BankAgency agency = BankAgency.getInstance();
        agency.addEmployee(new Employee("John", "Doe", "isDoe", "Manager"));
        IOperator operator = agency.getBankOperator("John", "Doe", "isDoe");

        if (operator != null) {
            ParticularAccountOwner owner1 = AccountOwnerFactory.createParticularAccountOwner(
                    "Jane", "324413131", "Praia, Cabo Verde", "Adina Freire", "0012e", "Cape Berdianu");
            ParticularAccountOwner owner2 = AccountOwnerFactory.createParticularAccountOwner(
                    "Francis", "320013131", "Tarrafal, Santiago, Cabo Verde", "Sanchu Neto", "0034e", "South African");

            OrdinaryParticularAccount acc1 = AccountFactory.createOrdinaryParticularAccount("Ordiacc", owner1, 100000, owner2);

            acc1.addCard(new Card("0003", owner1, acc1));

            operator.addNewAccountToTheBank(acc1);
            operator.addNewAccountToTheBank(AccountFactory.createOrdinaryParticularAccount("myOrdAcc", owner1, 230004, null));
            operator.addNewAccountToTheBank(AccountFactory.createOrdinaryParticularAccount("Foster", owner2, 12000, null));
        }
    }

    public static void main(String[] args) {
        launch();
    }
}