package com.groupnine.banku.controllers;

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

import java.io.IOException;
import java.util.List;

public class AccountsController {
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

    @FXML
    protected void dashboardBtnOnClick() throws IOException {
        WindowsContextController.showDashboardOnPrincipalStage();
    }

    @FXML
    protected void initialize() {
        createParticularAccountTable();
        refreshParticularAccountTable();
        createCorporativeAccountTable();
        refreshCorporativeAccountTable();
        createTemporaryAccountTable();
        refreshTemporaryAccountTable();
    }

    @FXML
    protected void tabPaneOnClick() {
        if (tabParticulares.isSelected()) {
            refreshParticularAccountTable();
        } else if (tabCorporativas.isSelected()) {
            refreshCorporativeAccountTable();
        } else if (tabTemporarias.isSelected()) {
                // TODO
        } else {
            Logger.log("At tabPaneOnClick event, unknown tab state", LogType.WARNING);
        }
    }

    private void createParticularAccountTable() {
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

    private void createCorporativeAccountTable() {
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

    private void createTemporaryAccountTable() {
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

    private void refreshParticularAccountTable() {
        if (!ordinaryAccountTableWasCreated) {
            createParticularAccountTable();
        }

        List<OrdinaryParticularAccount> accountList = BankAgency.getInstance().getOrdinaryAccountList();
        ObservableList<ParticularAccountBean> data = FXCollections.observableArrayList();

        for (OrdinaryParticularAccount acc : accountList) {
            data.add(new ParticularAccountBean(acc));
        }

        particularAccountsTable.setItems(data);
    }

    private void refreshCorporativeAccountTable() {
        if (!corporativeAccountTableWasCreated) {
            createCorporativeAccountTable();
        }

        List<EnterpriseAccount> accountList = BankAgency.getInstance().getEnterpriseAccountList();
        ObservableList<EnterpriseAccountBean> data = FXCollections.observableArrayList();

        for (EnterpriseAccount acc : accountList) {
            data.add(new EnterpriseAccountBean(acc));
        }

        corporativeAccountsTable.setItems(data);
    }

    private void refreshTemporaryAccountTable() {
        if (!temporaryAccountsTableWasCreated) {
            createTemporaryAccountTable();
        }

        List<TemporaryParticularAccount> accountList = BankAgency.getInstance().getTemporaryAccountList();
        ObservableList<TemporaryAccountBean> data = FXCollections.observableArrayList();

        for (TemporaryParticularAccount acc : accountList) {
            data.add(new TemporaryAccountBean(acc));
        }

        temporaryAccountsTable.setItems(data);
    }

    static private<B extends AccountBean, A extends Account> void removeSelectedAccountFromTable(TableView<B> table) {
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
    protected void removeBtnOnClick() {
        if (tabParticulares.isSelected()) {
            removeSelectedAccountFromTable(particularAccountsTable);
            refreshParticularAccountTable();
        } else if (tabCorporativas.isSelected()) {
            removeSelectedAccountFromTable(corporativeAccountsTable);
            refreshCorporativeAccountTable();
        } else if (tabTemporarias.isSelected()) {
            removeSelectedAccountFromTable(temporaryAccountsTable);
            refreshTemporaryAccountTable();
        } else {
            Logger.log("At removeBtnOnClick event, unknown tab state");
        }
    }

    public static abstract class AccountBean {
        final private SimpleStringProperty id;
        final private SimpleStringProperty nome;
        final private SimpleStringProperty dono;
        final private SimpleDoubleProperty balanco;
        final private SimpleDoubleProperty valorDoJuro;

        public AccountBean(final Account account) {
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
