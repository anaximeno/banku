package com.groupnine.banku.controllers;

import com.groupnine.banku.businesslogic.BankAgency;
import com.groupnine.banku.businesslogic.OrdinaryParticularAccount;
import com.groupnine.banku.businesslogic.ParticularAccountOwner;
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
    private TableView particularAccountsTable;
    @FXML
    private TableView corporativeAccountsTable;
    @FXML
    private TableView temporaryAccountsTable;

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
    }

    @FXML
    protected void tabPaneOnClick() {
        if (tabParticulares.isSelected()) {
            refreshParticularAccountTable();
        } else if (tabCorporativas.isSelected()) {
                // TODO
        } else if (tabTemporarias.isSelected()) {
                // TODO
        } else {
            System.out.println("WARN: At tabPaneOnClick event, unknown tab state");
        }
    }

    private void createParticularAccountTable() {
        particularAccountsTable.getColumns().clear();
        particularAccountsTable.setEditable(false);

        TableColumn accId = new TableColumn("Id");
        TableColumn accName = new TableColumn("Nome");
        TableColumn accOwner = new TableColumn("Dono");
        TableColumn accBalance = new TableColumn("Balanço");
        TableColumn accNCards = new TableColumn("#Cartões");
        TableColumn accAssociate = new TableColumn("Associado");
        TableColumn accInterest = new TableColumn("Valor do Juro");

        accId.setCellValueFactory(new PropertyValueFactory<ParticularAccountBean, String>("id"));
        accName.setCellValueFactory(new PropertyValueFactory<ParticularAccountBean, String>("nome"));
        accOwner.setCellValueFactory(new PropertyValueFactory<ParticularAccountBean, String>("dono"));
        accBalance.setCellValueFactory(new PropertyValueFactory<ParticularAccountBean, Double>("balanco"));
        accNCards.setCellValueFactory(new PropertyValueFactory<ParticularAccountBean, Integer>("cartoes"));
        accAssociate.setCellValueFactory(new PropertyValueFactory<ParticularAccountBean, String>("associado"));
        accInterest.setCellValueFactory(new PropertyValueFactory<ParticularAccountBean, String>("valorDoJuro"));

        particularAccountsTable.getColumns().addAll(accId, accName, accOwner, accBalance, accNCards, accAssociate, accInterest);
        ordinaryAccountTableWasCreated = true;
    }



    private void refreshParticularAccountTable() {
        if (ordinaryAccountTableWasCreated == false) {
            createParticularAccountTable();
        }

        List<OrdinaryParticularAccount> accountList = BankAgency.getInstance().getOrdinaryAccountList();
        ObservableList<ParticularAccountBean> data = FXCollections.observableArrayList();

        for (OrdinaryParticularAccount acc : accountList) {
            data.add(new ParticularAccountBean(acc));
        }

        particularAccountsTable.setItems(data);
    }

    private void removeSelectedParticularAccount() {
        BankAgency agency = BankAgency.getInstance();

        ParticularAccountBean acc = (ParticularAccountBean) particularAccountsTable.getSelectionModel().getSelectedItem();
        if (acc != null) {
            OrdinaryParticularAccount account = (OrdinaryParticularAccount) agency.findAccountByNumber(acc.getId());

            if (account != null) {
                String id = account.getAccountNumber();

                agency.getClientAccounts().remove(account);
                
                System.out.println("Account '" + id + "' was removed!");
                // TODO: Notify user that account was removed?
            }
        } else {
            // TODO: Alert user that a line must be selected
        }
    }

    @FXML
    protected void removeBtnOnClick() {
        if (tabParticulares.isSelected()) {
            removeSelectedParticularAccount();
            refreshParticularAccountTable();
        } else if (tabCorporativas.isSelected()) {
            // TODO
        } else if (tabTemporarias.isSelected()) {
            // TODO
        } else {
            System.out.println("WARN: At removeBtnOnClick event, unknown tab state");
        }
    }

    public static class ParticularAccountBean {
        final private SimpleStringProperty id;
        final private SimpleStringProperty nome;
        final private SimpleStringProperty dono;
        final private SimpleDoubleProperty balanco;
        final private SimpleIntegerProperty cartoes;
        final private SimpleStringProperty associado;
        final private SimpleDoubleProperty valorDoJuro;

        public ParticularAccountBean(final OrdinaryParticularAccount account) {
            this.id = new SimpleStringProperty(account.getAccountNumber());
            this.nome = new SimpleStringProperty(account.getAccountName());
            ParticularAccountOwner owner = (ParticularAccountOwner) account.getOwner();
            this.dono = new SimpleStringProperty(owner.getFullName());
            this.balanco = new SimpleDoubleProperty(account.getAccountBalance());
            this.cartoes = new SimpleIntegerProperty(account.getAllCards().size());
            ParticularAccountOwner associate = account.getMinorAccountAssociate();
            this.associado = new SimpleStringProperty(associate != null ? associate.getFullName() : "-");
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

        public int getCartoes() {
            return cartoes.get();
        }

        public void setCartoes(int value) {
            this.cartoes.set(value);
        }

        public String getAssociado() {
            return associado.get();
        }

        public void setAssociado(String associado) {
            this.associado.set(associado);
        }

        public double getValorDoJuro() {
            return valorDoJuro.get();
        }

        public void setValorDoJuro(double valor) {
            valorDoJuro.set(valor);
        }
    }
}
