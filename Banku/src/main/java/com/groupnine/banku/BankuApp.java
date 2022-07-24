/* Disciplina: PROGRAMAÇÃO ORIENTADO A OBJETOS (POO)
Projeto: Sistemas de gestão de contas bancárias
Data de criação: 25-05-2022
Data de modificação: 24-07-2022
Versão:1.0
Autor(es): Anaximeno Brito, Carlos Monteiro, Marco Costa e Tamiris Evora
Faculdade de Ciências e Tecnologia
Universidade de Cabo Verde */
package com.groupnine.banku;

import com.groupnine.banku.businesslogic.*;
import com.groupnine.banku.controllers.WindowContextController;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;


public class BankuApp extends Application {
    private static WindowContextController mainWindow;
    private AutomaticInterestHandler interestHandler;

    // todo: update fact usage
    final public static AccountFactory globalAccFactory =  new AccountFactory(0.01, 0.05, 0.08);
    final public static AccountOwnerFactory globalAccOwnFactory = new AccountOwnerFactory();
    final public static Faker globalFaker = new Faker();

    public static IOperator currentOperator;

    private final static int MAX_GEN_ITERATION = 100;

    private static void setMainWindow(WindowContextController window)
    {
        mainWindow = window;
    }

    public static WindowContextController getMainWindow()
    {
        return mainWindow;
    }

    public void initInterestHandler()
    {
        interestHandler = new AutomaticInterestHandler();
        interestHandler.start();

        if (mainWindow != null) {
            mainWindow.getStage().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e -> interestHandler.interrupt());
        }
    }

    private static ParticularAccountOwner generateNewFakeAccountOwner()
    /* Retorna uma classe ParticularAccountOwner generada com dados falsos. */
    {
        final String name = globalFaker.name().firstName();
        final String lastName = globalFaker.name().lastName();
        final String NIF = globalFaker.regexify("[0-9]{13}");
        final String address = globalFaker.address().fullAddress();
        final String nationality = globalFaker.address().country();
        return globalAccOwnFactory.createParticularAccountOwner(name, lastName, NIF, address, nationality);
    }

    private static OrdinaryParticularAccount generateOrdinaryAccount(ParticularAccountOwner owner)
    {
        final BankAgency agency = BankAgency.getInstance();
        final IOperator operator = agency.getBankOperator("JohnDoe", "isDoe");

        final String accountName = globalFaker.dragonBall().character();
        final int balance = globalFaker.number().numberBetween(5000, 800000);

        ParticularAccountOwner associate = null;

        if (globalFaker.random().nextBoolean()) {
            associate = generateNewFakeAccountOwner();
            operator.addNewClientToTheBank(associate);
        }

        return globalAccFactory.createOrdinaryParticularAccount(accountName, owner, balance, associate);
    }

    private static TemporaryParticularAccount generateTemporaryAccount(ParticularAccountOwner owner)
    {
        final String accountName = globalFaker.pokemon().name();
        final double balance = globalFaker.number().numberBetween(50000, 300000);
        final ZoneId zoneId = ZoneId.systemDefault();
        final Date now = Date.from(LocalDate.now().atStartOfDay(zoneId).toInstant());
        final Date expiration = globalFaker.date().future(366, TimeUnit.DAYS, now);
        final LocalDate expirationDate = expiration.toInstant().atZone(zoneId).toLocalDate();
        final boolean hasBoost = globalFaker.random().nextBoolean();

        TemporaryParticularAccount acc = globalAccFactory.createTemporaryParticularAccount(accountName, balance, owner, expirationDate);

        if (hasBoost)
            acc.activateBoost();

        return acc;
    }

    private static EnterpriseAccountOwner generateEnterpriseAccountOwner()
    {
        final String name = globalFaker.company().name();
        final String NIF = globalFaker.regexify("[0-9]{13}");
        final String address = globalFaker.address().fullAddress();

        ArrayList<EnterprisePartner> partners = new ArrayList<>();

        final int numberOfPartners = globalFaker.random().nextInt(1, 15);

        for (int i = 0 ; i < numberOfPartners ; i += 1) {
            final String partnerName = globalFaker.lordOfTheRings().character();
            final String cni =  globalFaker.regexify("[A-Z0-9]{12}");
            partners.add(new EnterprisePartner(partnerName, cni));
        }

        return globalAccOwnFactory.createEnterpriseAccountOwner(name, NIF, address, partners);
    }

    private static EnterpriseAccount generateEnterpriseAccount(EnterpriseAccountOwner owner)
    {
        final String name = globalFaker.zelda().character();
        final double balance = globalFaker.random().nextInt(30000, 1000000000);
        return globalAccFactory.createEnterpriseAccount(name, owner, generateNewFakeAccountOwner(), balance);
    }

    private static Account getRandomAccount() {
        final BankAgency agency = BankAgency.getInstance();
        Account account = null;

        do {
            final String accId = SequentiableFactory.encodeNumber(globalFaker.random().nextInt(
                    MAX_GEN_ITERATION - 1), SequentiableFactory.ID_PLACES);
            account = agency.findAccountByNumber(accId);
        } while (account == null);

        return account;
    }

    private static void makeFakeDeposit()
    {
        final Account account = getRandomAccount();
        final int value = globalFaker.random().nextInt(100, account.getAccountBalance().intValue() - 1000);
        if (value > 0)
            currentOperator.makeMoneyDeposit(account, value);
    }

    private static void makeFakeWithdraw()
    {
        final Account account = getRandomAccount();
        final int value = globalFaker.random().nextInt(100, account.getAccountBalance().intValue() - 1000);
        if (value > 0)
            currentOperator.makeMoneyWithdraw(account, value);
    }

    private static void makeFakeTransference()
    {
        final Account account1 = getRandomAccount();
        final Account account2 = getRandomAccount();
        if (account2 != account1) {
            final int value = globalFaker.random().nextInt(100, account1.getAccountBalance().intValue() - 1000);

            if (value > 0)
                currentOperator.makeTransaction(account1, account2, value);
        }
    }

    public void initOperator() {
        final BankAgency agency = BankAgency.getInstance();

        agency.addEmployee(new Employee("John", "Doe", "isDoe", "Manager"));
        agency.addEmployee(new Employee("Background", "Thread",  "multi-threading", "Automatic Interest Applicator"));

        currentOperator = agency.getBankOperator("JohnDoe", "isDoe");
    }

    public static void initData()
    {
        final BankAgency agency = BankAgency.getInstance();

        if (currentOperator != null) {
            for (int i = 0 ; i < MAX_GEN_ITERATION ; i += 1) {
                switch (globalFaker.random().nextInt(1, 3)) {
                    case 1 -> currentOperator.addNewAccountToTheBank(generateOrdinaryAccount(generateNewFakeAccountOwner()));
                    case 2 -> currentOperator.addNewAccountToTheBank(generateTemporaryAccount(generateNewFakeAccountOwner()));
                    case 3 -> currentOperator.addNewAccountToTheBank(generateEnterpriseAccount(generateEnterpriseAccountOwner()));
                }
            }

            final int maxOp = (MAX_GEN_ITERATION / 2);

            for (int i = 0 ; i < maxOp ; i += 1) {
                switch (globalFaker.random().nextInt(1, 3)) {
                    case 1 -> makeFakeDeposit();
                    case 2 -> makeFakeWithdraw();
                    case 3 -> makeFakeTransference();
                }
            }
        }
    }

    @Override
    public void start(Stage stage)
    {
        initOperator();
        initData();
        setMainWindow(new WindowContextController("views/dashboard-view.fxml", "Banku - Dashboard"));
        getMainWindow().showDefaultView();
        initInterestHandler();
    }

    public static void main(String[] args) {
        launch();
    }
}