package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;
import com.groupnine.banku.miscellaneous.IBasicFilter;
import com.groupnine.banku.miscellaneous.ListUtils;
import com.groupnine.banku.miscellaneous.LogType;
import com.groupnine.banku.miscellaneous.Logger;
import com.groupnine.banku.businesslogic.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AccountsController {
    private static AccountsController activeInstance;

    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextField searchInput;

    @FXML
    private Tab tabParticulares;
    @FXML
    private Tab tabCorporativas;
    @FXML
    private Tab tabTemporarias;

    @FXML
    private TableView<ParticularAccountBean> particularAccountsTable;
    @FXML
    private TableView<EnterpriseAccountBean> corporativeAccountsTable;
    @FXML
    private TableView<TemporaryAccountBean> temporaryAccountsTable;

    private boolean ordinaryAccountTableWasCreated = false;
    private boolean corporativeAccountTableWasCreated = false;
    private boolean temporaryAccountsTableWasCreated = false;

    public static AccountsController getActiveInstance()
    /* Retorna a instancia ativa e inicialiada dessa view. */
    {
        return activeInstance;
    }

    @FXML
    protected void dashboardBtnOnClick()
    {
        BankuApp.getMainWindow().showDefaultView();
    }

    @FXML
    protected void reloadBtnOnClick()
    {
        refreshTables();
    }

    @FXML
    protected void createButtonOnClick()
    {
        String viewPath = "", title = "Banku - ";

        if (tabParticulares.isSelected()) {
            viewPath = "views/add-new-particular-account-view.fxml";
            title += " Adicionar Nova Conta Particular";
            AddNewParticularAccountController.activeInstance = new WindowContextController(430, 617, viewPath, title);
            AddNewParticularAccountController.activeInstance.showDefaultView();
        } else if (tabCorporativas.isSelected()) {
            viewPath = "views/add-new-enterprise-account-view.fxml";
            title += " Adicionar Nova Conta Corporativa";
            AddNewEnterpriseAccountController.activeInstance = new WindowContextController(435, 617, viewPath, title);
            AddNewEnterpriseAccountController.activeInstance.showDefaultView();
        } else if (tabTemporarias.isSelected()) {
            viewPath = "views/add-new-temporary-account-view.fxml";
            title += " Adicionar Nova Conta Temporária";
            AddNewTemporaryAccountController.activeInstance = new WindowContextController(435, 617, viewPath, title);
            AddNewTemporaryAccountController.activeInstance.showDefaultView();
        } else {
            Logger.log("Unknown tab state at createButtonOnClick in AccountsController", LogType.WARNING);
        }
    }

    @FXML
    protected void initialize()
    {
        comboBox.getItems().add("Nome");
        comboBox.getItems().add("Número");

        /* default */
        comboBox.setValue("Nome");


        createTables();
        refreshTables();

        comboBox.setOnMouseExited(e -> tabPaneOnClick());
        searchInput.textProperty().addListener((obs, old, niu) -> tabPaneOnClick());

        /* Isto vai ser utilizado externamente para
         * atualizar o estado dessa view.
         * */
        activeInstance = this;
    }

    private void createTables()
    /* Cria todas as tabelas dessa view. */
    {
        createParticularAccountTable();
        createCorporativeAccountTable();
        createTemporaryAccountTable();
    }

    public void refreshTables()
    /* Atualiza todas a tabelas. Este método é público pois
     * pode ser utilizado externamente. */
    {
        refreshParticularAccountTable();
        refreshCorporativeAccountTable();
        refreshTemporaryAccountTable();
    }

    @FXML
    protected void tabPaneOnClick()
    {
        if (tabParticulares.isSelected()) {
            refreshParticularAccountTable();
        } else if (tabCorporativas.isSelected()) {
            refreshCorporativeAccountTable();
        } else if (tabTemporarias.isSelected()) {
            refreshTemporaryAccountTable();
        } else {
            Logger.log("At tabPaneOnClick event, unknown tab state", LogType.WARNING);
        }
    }

    private void createParticularAccountTable()
    {
        particularAccountsTable.getColumns().clear();
        particularAccountsTable.setEditable(false);

        TableColumn<ParticularAccountBean, String> accId = new TableColumn<>("Id");
        TableColumn<ParticularAccountBean, String>  accName = new TableColumn<>("Nome");
        TableColumn<ParticularAccountBean, String>  accOwner = new TableColumn<>("Dono");
        TableColumn<ParticularAccountBean, Double>  accBalance = new TableColumn<>("Balanço");
        TableColumn<ParticularAccountBean, Integer>  accNCards = new TableColumn<>("#Cartões");
        TableColumn<ParticularAccountBean, String>  accAssociate = new TableColumn<>("Associado");
        TableColumn<ParticularAccountBean, String>  accInterest = new TableColumn<>("Valor do Juro");

        accId.setCellValueFactory(new PropertyValueFactory<>("id"));
        accName.setCellValueFactory(new PropertyValueFactory<>("nome"));
        accOwner.setCellValueFactory(new PropertyValueFactory<>("dono"));
        accBalance.setCellValueFactory(new PropertyValueFactory<>("balanco"));
        accNCards.setCellValueFactory(new PropertyValueFactory<>("numCards"));
        accAssociate.setCellValueFactory(new PropertyValueFactory<>("associado"));
        accInterest.setCellValueFactory(new PropertyValueFactory<>("valorDoJuro"));

        particularAccountsTable.getColumns().addAll(accId, accName, accOwner, accBalance, accNCards, accAssociate, accInterest);
        ordinaryAccountTableWasCreated = true;
    }

    private void createCorporativeAccountTable()
    {
        corporativeAccountsTable.getColumns().clear();
        corporativeAccountsTable.setEditable(false);

        TableColumn<EnterpriseAccountBean, String> accId  = new TableColumn<>("Id");
        TableColumn<EnterpriseAccountBean, String> accName = new TableColumn<>("Nome");
        TableColumn<EnterpriseAccountBean, String> accOwner = new TableColumn<>("Dono");
        TableColumn<EnterpriseAccountBean, String> accAdmin = new TableColumn<>("Admin");
        TableColumn<EnterpriseAccountBean, Double> accBal = new TableColumn<>("Balanço");
        TableColumn<EnterpriseAccountBean, Integer> accNAutori = new TableColumn<>("#Autorizados");
        TableColumn<EnterpriseAccountBean, Double> accInterest = new TableColumn<>("Valor do Juro");

        accId.setCellValueFactory(new PropertyValueFactory<>("id"));
        accName.setCellValueFactory(new PropertyValueFactory<>("nome"));
        accOwner.setCellValueFactory(new PropertyValueFactory<>("dono"));
        accAdmin.setCellValueFactory(new PropertyValueFactory<>("admin"));
        accBal.setCellValueFactory(new PropertyValueFactory<>("balanco"));
        accNAutori.setCellValueFactory(new PropertyValueFactory<>("nAutorizados"));
        accInterest.setCellValueFactory(new PropertyValueFactory<>("valorDoJuro"));

        corporativeAccountsTable.getColumns().addAll(accId, accName, accOwner, accAdmin, accBal, accNAutori, accInterest);
        corporativeAccountTableWasCreated = true;
    }

    private void createTemporaryAccountTable()
    {
        temporaryAccountsTable.getColumns().clear();
        temporaryAccountsTable.setEditable(false);

        TableColumn<TemporaryAccountBean, String> accId  = new TableColumn<>("Id");
        TableColumn<TemporaryAccountBean, String> accName = new TableColumn<>("Nome");
        TableColumn<TemporaryAccountBean, String> accOwner = new TableColumn<>("Dono");
        TableColumn<TemporaryAccountBean, Double> accBal = new TableColumn<>("Balanço");
        TableColumn<TemporaryAccountBean, String> accCreat = new TableColumn<>("Data de criação");
        TableColumn<TemporaryAccountBean, String> accExpir = new TableColumn<>("Data de expiração");
        TableColumn<TemporaryAccountBean, String> accBoost = new TableColumn<>("Reforço");
        //TableColumn<TemporaryAccountBean, Double> accInterest = new TableColumn<>("Valor do Juro");

        accId.setCellValueFactory(new PropertyValueFactory<>("id"));
        accName.setCellValueFactory(new PropertyValueFactory<>("nome"));
        accOwner.setCellValueFactory(new PropertyValueFactory<>("dono"));
        accBal.setCellValueFactory(new PropertyValueFactory<>("balanco"));
        accCreat.setCellValueFactory(new PropertyValueFactory<>("dataDeCriacao"));
        accExpir.setCellValueFactory(new PropertyValueFactory<>("dataDeExpiracao"));
        accBoost.setCellValueFactory(new PropertyValueFactory<>("reforco"));
        //accInterest.setCellValueFactory(new PropertyValueFactory<>("valorDoJuro"));

        temporaryAccountsTable.getColumns().addAll(accId, accName, accOwner, accBal, accCreat, accExpir, accBoost /*, accInterest */);
        temporaryAccountsTableWasCreated = false;
    }

    private<T extends Account> List<T>
    filterAccountList(List<T> accountList, String entry, IBasicFilter<T, String> filter)
    /* Filtra uma lista de contas, usando um método de filtragem fornecido pelo utilizador. */
    {
        List<T> list = new ArrayList<>();

        for (T item : accountList) {
            if (filter.passesFilter(item, entry)) {
                list.add(item);
            }
        }

        return list;
    }

    private<T extends Account>  List<T>
    applyFilter(List<T> accountList, String searchEntry)
    /* Retorna a lista filtrada. */
    {
        List<T> result = filterAccountList(accountList, searchEntry, (account, entry) -> {
            if (comboBox.getValue().equals("Nome")) {
                return account.getAccountName().toLowerCase().contains(entry.toLowerCase());
            } else {
                return account.getAccountNumber().contains(entry);
            }
        });
        result.sort((T a, T b) -> {
            if (comboBox.getValue().equals("Nome")) {
                return a.getAccountName().compareTo(b.getAccountName());
            } else {
                return a.getAccountNumber().compareTo(b.getAccountNumber());
            }
        });
        return result;
    }

    private void refreshParticularAccountTable()
    {
        if (!ordinaryAccountTableWasCreated) {
            createParticularAccountTable();
        }

        List<OrdinaryParticularAccount> accountList = BankAgency.getInstance().getOrdinaryAccountList();

        String searchEntry = searchInput.getText();

        if (searchEntry != null && !searchEntry.isEmpty()) {
            accountList = applyFilter(accountList, searchEntry);
        }

        ObservableList<ParticularAccountBean> data = FXCollections.observableArrayList();

        for (OrdinaryParticularAccount acc : accountList) {
            data.add(new ParticularAccountBean(acc));
        }

        particularAccountsTable.setItems(data);
    }

    private void refreshCorporativeAccountTable()
    {
        if (!corporativeAccountTableWasCreated) {
            createCorporativeAccountTable();
        }

        List<EnterpriseAccount> accountList = BankAgency.getInstance().getEnterpriseAccountList();

        String searchEntry = searchInput.getText();

        if (searchEntry != null && !searchEntry.isEmpty()) {
            accountList = applyFilter(accountList, searchEntry);
        }

        ObservableList<EnterpriseAccountBean> data = FXCollections.observableArrayList();

        for (EnterpriseAccount acc : accountList) {
            data.add(new EnterpriseAccountBean(acc));
        }

        corporativeAccountsTable.setItems(data);
    }

    private void refreshTemporaryAccountTable()
    {
        if (!temporaryAccountsTableWasCreated) {
            createTemporaryAccountTable();
        }

        List<TemporaryParticularAccount> accountList = BankAgency.getInstance().getTemporaryAccountList();

        String searchEntry = searchInput.getText();

        if (searchEntry != null && !searchEntry.isEmpty()) {
            accountList = applyFilter(accountList, searchEntry);
        }

        ObservableList<TemporaryAccountBean> data = FXCollections.observableArrayList();

        for (TemporaryParticularAccount acc : accountList) {
            data.add(new TemporaryAccountBean(acc));
        }

        temporaryAccountsTable.setItems(data);
    }

    static private<B extends AccountBean, A extends Account> void removeSelectedAccountFromTable(TableView<B> table)
    {
        BankAgency agency = BankAgency.getInstance();

        B acc = (B) table.getSelectionModel().getSelectedItem();
        if (acc != null) {
            A account = (A) agency.findAccountByNumber(acc.getId());

            if (account != null) {
                String id = account.getAccountNumber();

                agency.getClientAccounts().remove(account);

                Logger.log("Account '" + id + "' was removed", LogType.INFO);
                // TODO: Notify user that account was removed?
            }
        } else {
            // TODO: Alert user that a line must be selected
            Logger.log("Remove button clicker with no line selected, nothing done");
        }
    }

    @FXML
    protected void removeBtnOnClick()
    {
        if (tabParticulares.isSelected()) {
            removeSelectedAccountFromTable(particularAccountsTable);
        } else if (tabCorporativas.isSelected()) {
            removeSelectedAccountFromTable(corporativeAccountsTable);
        } else if (tabTemporarias.isSelected()) {
            removeSelectedAccountFromTable(temporaryAccountsTable);
        } else {
            Logger.log("At removeBtnOnClick event, unknown tab state");
        }
        tabPaneOnClick();
    }

    public static abstract class AccountBean {
        final private SimpleStringProperty id;
        final private SimpleStringProperty nome;
        final private SimpleStringProperty dono;
        final private SimpleDoubleProperty balanco;
        final private SimpleDoubleProperty valorDoJuro;

        public AccountBean(final Account account)
        {
            this.id = new SimpleStringProperty(account.getAccountNumber());
            this.nome = new SimpleStringProperty(account.getAccountName());

            if (account.getOwner() instanceof ParticularAccountOwner owner) {
                this.dono = new SimpleStringProperty(owner.getFullName());
            } else if (account.getOwner() instanceof EnterpriseAccountOwner owner) {
                this.dono = new SimpleStringProperty(owner.getName());
            } else {
                this.dono = new SimpleStringProperty("Undefined!");
                Logger.log("Undefined AccountOwner type at Account Bean instantiation!", LogType.WARNING);
            }

            this.balanco = new SimpleDoubleProperty(account.getAccountBalance());
            this.valorDoJuro = new SimpleDoubleProperty(account.getInterestRate());
        }

        public String getId() {
            return id.get();
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public String getNome() {
            return nome.get();
        }

        public void setNome(String nome) {
            this.nome.set(nome);
        }

        public String getDono() {
            return dono.get();
        }

        public void setDono(String dono) {
            this.dono.set(dono);
        }

        public double getBalanco() {
            return balanco.get();
        }

        public void setBalanco(double balanco) {
            this.balanco.set(balanco);
        }

        public double getValorDoJuro() {
            return valorDoJuro.get();
        }

        public void setValorDoJuro(double valor) {
            valorDoJuro.set(valor);
        }
    }

    public static class ParticularAccountBean extends AccountBean {
        final private SimpleIntegerProperty numCards;
        final private SimpleStringProperty associado;

        public ParticularAccountBean(final OrdinaryParticularAccount account) {
            super(account);
            this.numCards = new SimpleIntegerProperty(ListUtils.lengthOf(account.getAllCards()));
            ParticularAccountOwner associate = account.getMinorAccountAssociate();
            this.associado = new SimpleStringProperty(associate != null ? associate.getFullName() : "-");
        }

        public int getNumCards() {
            return numCards.get();
        }

        public void setNumCards(int value) {
            this.numCards.set(value);
        }

        public String getAssociado() {
            return associado.get();
        }

        public void setAssociado(String associado) {
            this.associado.set(associado);
        }
    }

    public static class EnterpriseAccountBean extends AccountBean {
        final private SimpleStringProperty admin;
        final private SimpleIntegerProperty nAutorizados;

        EnterpriseAccountBean(final EnterpriseAccount account) {
            super(account);
            ParticularAccountOwner admin = account.getAdmin();
            this.admin = new SimpleStringProperty(admin != null ? admin.getFullName() : "-");
            this.nAutorizados = new SimpleIntegerProperty(ListUtils.lengthOf(account.getAuthorizedUsers()));
        }

        public String getAdmin() {
            return admin.get();
        }

        public void setAdmin(String admin) {
            this.admin.set(admin);
        }

        public int getNAutorizados() {
            return nAutorizados.get();
        }

        public void setNAutorizados(int n) {
            this.nAutorizados.set(n);
        }
    }

    public static class TemporaryAccountBean extends AccountBean {
        final private SimpleStringProperty dataDeCriacao;
        final private SimpleStringProperty dataDeExpiracao;
        final private SimpleStringProperty reforco;

        public TemporaryAccountBean(TemporaryParticularAccount account) {
            super((Account) account);
            this.dataDeCriacao = new SimpleStringProperty(account.getCreationDate().toString());
            this.dataDeExpiracao = new SimpleStringProperty(account.getExpirationDate().toString());
            this.reforco = new SimpleStringProperty(account.hasBoost() ? "Sim" : "Não");
        }

        public String getDataDeCriacao() {
            return dataDeCriacao.get();
        }

        public void setDataDeCriacao(String data) {
            dataDeCriacao.set(data);
        }

        public String getDataDeExpiracao() {
            return dataDeExpiracao.get();
        }

        public void setDataDeExpiracao(String data) {
            dataDeExpiracao.set(data);
        }

        public String getReforco() {
            return reforco.get();
        }

        public void setReforco(String valor) {
            reforco.set(valor);
        }
    }
}
