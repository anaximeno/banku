package com.groupnine.banku;

import com.groupnine.banku.businesslogic.*;
import com.groupnine.banku.controllers.WindowsContextController;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

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
            ParticularAccountOwner owner1 = AccountOwnerFactory.createParticularAccountOwner("Jane", "324413131", "Praia, Cabo Verde", "Adina Freire", "0012e", "Cape Berdianu");
            ParticularAccountOwner owner2 = AccountOwnerFactory.createParticularAccountOwner("Francis", "320013131", "Tarrafal, Santiago, Cabo Verde", "Sanchu Neto", "0034e", "South African");
            OrdinaryParticularAccount acc1 = AccountFactory.createOrdinaryParticularAccount("Ordiacc", owner1, 100000, owner2);

            acc1.addCard(owner1, "001");
            acc1.addCard(owner2, "002");

            operator.addNewAccountToTheBank(acc1);
            operator.addNewAccountToTheBank(AccountFactory.createOrdinaryParticularAccount("myOrdAcc", owner1, 230004, null));
            operator.addNewAccountToTheBank(AccountFactory.createOrdinaryParticularAccount("Foster", owner2, 12000, null));

            EnterpriseAccountOwner eOwner = AccountOwnerFactory.createEnterpriseAccountOwner("LuckeLuke CO", "234124124", "The Place", new ArrayList<>());

            operator.addNewAccountToTheBank(AccountFactory.createEnterpriseAccount("LuckeLukeAcc01", eOwner, owner2, 10000000));

            ParticularAccountOwner owner3 = AccountOwnerFactory.createParticularAccountOwner(
                            "Francisco", "3311213131", "Santa Catarina, Cabo Verde", "Rodrigues", "0012e", "Cabo Verdiana");
            TemporaryParticularAccount account3 = AccountFactory.createTemporaryParticularAccount(
                    "MyTempAcc", 65000d, owner3, LocalDate.of(2023, 5, 23));
            BankAgency.getInstance().addNewAccount(account3);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}