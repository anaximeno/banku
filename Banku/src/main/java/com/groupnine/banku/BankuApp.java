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

    // todo: update fact usage
    public static AccountFactory globalAccFactory;
    public static AccountOwnerFactory globalAccOwnFactory;
    public static IOperator currentOperator;

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
        setMainWindow(new WindowContextController("views/dashboard-view.fxml", "Banku - Dashboard"));
        initInterestHandler();
        getMainWindow().showDefaultView();
    }

    public static void initData() {
        final AccountFactory accFact = new AccountFactory(0.01, 0.05, 0.08);
        final AccountOwnerFactory accOwnFact = new AccountOwnerFactory();
        globalAccFactory = accFact;
        globalAccOwnFactory = accOwnFact;
        final BankAgency agency = BankAgency.getInstance();

        agency.addEmployee(new Employee("John", "Doe", "isDoe", "Manager"));
        agency.addEmployee(new Employee("Background", "Thread",  "multi-threading", "Automatic Interest Applicator"));

        final IOperator operator = agency.getBankOperator("John", "Doe", "isDoe");
        currentOperator = operator;

        if (operator != null) {
            ParticularAccountOwner owner1 = accOwnFact.createParticularAccountOwner("Jane", "324413131", "Praia, Cabo Verde", "Adina Freire", "0012e", "Cape Berdianu");
            ParticularAccountOwner owner2 = accOwnFact.createParticularAccountOwner("Francis", "320013131", "Tarrafal, Santiago, Cabo Verde", "Sanchu Neto", "0034e", "South African");
            EnterpriseAccountOwner owner3 = accOwnFact.createEnterpriseAccountOwner("LuckeLuke CO", "234124124", "The Place", new ArrayList<>());
            ParticularAccountOwner owner4 = accOwnFact.createParticularAccountOwner("Francisco", "3311213131", "Santa Catarina, Cabo Verde", "Rodrigues", "0012e", "Cabo Verdiana");

            operator.addNewClientToTheBank(owner1);
            operator.addNewClientToTheBank(owner2);
            operator.addNewClientToTheBank(owner3);
            operator.addNewClientToTheBank(owner4);

            OrdinaryParticularAccount acc1 = accFact.createOrdinaryParticularAccount("Ordiacc", owner1, 100000, owner2);
            OrdinaryParticularAccount acc2 = accFact.createOrdinaryParticularAccount("myOrdAcc", owner1, 230004, null);
            OrdinaryParticularAccount acc3 = accFact.createOrdinaryParticularAccount("Foster", owner2, 12000, null);
            EnterpriseAccount acc4 = accFact.createEnterpriseAccount("LuckeLukeAcc01", owner3, owner2, 10000000);
            final LocalDate expDate = LocalDate.of(2023, 5, 23);
            TemporaryParticularAccount acc5 = accFact.createTemporaryParticularAccount("MyTempAcc", 65000d, owner4, expDate);

            acc1.addCard(owner1, "001");
            acc1.addCard(owner2, "002");

            operator.addNewAccountToTheBank(acc1);
            operator.addNewAccountToTheBank(acc2);
            operator.addNewAccountToTheBank(acc3);
            operator.addNewAccountToTheBank(acc4);
            operator.addNewAccountToTheBank(acc5);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}