package com.groupnine.banku;

import com.groupnine.banku.businesslogic.*;
import com.groupnine.banku.controllers.WindowContextController;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.time.LocalDate;
import java.util.ArrayList;
import com.github.javafaker.Faker;


public class BankuApp extends Application {
    private static WindowContextController mainWindow;
    private AutomaticInterestHandler interestHandler;

    // todo: update fact usage
    final public static AccountFactory globalAccFactory =  new AccountFactory(0.01, 0.05, 0.08);
    final public static AccountOwnerFactory globalAccOwnFactory = new AccountOwnerFactory();
    final public static Faker globalFaker = new Faker();

    public static IOperator currentOperator;

    private final static int MAX_GEN_ITERATION = 20;

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

    private static ParticularAccountOwner generateNewFakeAccountOwner()
    /* Retorna uma classe ParticularAccountOwner generada com dados falsos. */
    {
        final String name = globalFaker.name().firstName();
        final String lastName = globalFaker.name().lastName();
        final String address = globalFaker.address().fullAddress();
        final String id = globalFaker.regexify("[A-Z0-9]{12}");
        final String NIF = globalFaker.regexify("[0-9]{13}");
        final String nationality = globalFaker.address().country();
        return globalAccOwnFactory.createParticularAccountOwner(name, NIF, address, lastName, id, nationality);
    }

    private static OrdinaryParticularAccount generateOrdinaryAccount(ParticularAccountOwner owner) {
        final BankAgency agency = BankAgency.getInstance();
        final IOperator operator = agency.getBankOperator("John", "Doe", "isDoe");

        final String accountName = globalFaker.funnyName().name();
        final int balance = globalFaker.number().numberBetween(5000, 800000);

        ParticularAccountOwner associate = null;

        if (globalFaker.random().nextBoolean()) {
            associate = generateNewFakeAccountOwner();
            operator.addNewClientToTheBank(associate);
        }

        return globalAccFactory.createOrdinaryParticularAccount(accountName, owner, balance, associate);
    }

    public static void initData() {
        final AccountFactory accFact = globalAccFactory;
        final AccountOwnerFactory accOwnFact = globalAccOwnFactory;
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

            for (int i = 0 ; i < MAX_GEN_ITERATION ; i += 1) {
                ParticularAccountOwner owner = generateNewFakeAccountOwner();
                OrdinaryParticularAccount account = generateOrdinaryAccount(owner);
                operator.addNewClientToTheBank(owner);
                operator.addNewAccountToTheBank(account);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}