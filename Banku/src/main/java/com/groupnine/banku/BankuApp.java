package com.groupnine.banku;

import com.groupnine.banku.businesslogic.*;
import com.groupnine.banku.controllers.WindowContextController;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.time.LocalDate;
import java.util.ArrayList;

public class BankuApp extends Application {
    private static WindowContextController mainWindow;
    private AutomaticInterestHandler interestHandler;

    private static void setMainWindow(WindowContextController window)
    {
        mainWindow = window;
    }

    public static WindowContextController getMainWindow()
    {
        return mainWindow;
    }

    public void initInterestHandler() {
        WindowContextController window = getMainWindow();
        interestHandler = new AutomaticInterestHandler();
        interestHandler.start();

        if (window != null) {
            window.getStage().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent -> interestHandler.interrupt());
        }
    }

    @Override
    public void start(Stage stage)
    {
        initData();

        setMainWindow(new WindowContextController("views/dashboard-view.fxml", "Banku - Dashboard", "css/theme"));

        initInterestHandler();
        getMainWindow().show();
    }

    public static void initData() {
        Employee interestHandlerEmp = new Employee("Background", "Thread",  "multi-threading", "Automatic Interest Applicator");
        BankAgency.getInstance().addEmployee(interestHandlerEmp);

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