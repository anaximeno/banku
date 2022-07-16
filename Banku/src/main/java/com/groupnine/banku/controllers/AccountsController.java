package com.groupnine.banku.controllers;

import com.groupnine.banku.businesslogic.BankAgency;
import com.groupnine.banku.businesslogic.OrdinaryParticularAccount;
import com.groupnine.banku.businesslogic.ParticularAccountOwner;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class AccountsController {
    @FXML
    private Button dashboardBtn;
    @FXML
    private Button carregarBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private TableView particularAccountsTable;

    private boolean ordinaryAccountTableWasCreated;

    @FXML
    protected void initialize() {
        createOrdinaryAccountTable();
        refreshOrdinaryParticularAccountTable();
    }

    protected void createOrdinaryAccountTable() {
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

    protected void refreshOrdinaryParticularAccountTable() {
        if (ordinaryAccountTableWasCreated == false) {
            createOrdinaryAccountTable();
        }

        ObservableList<ParticularAccountBean> data = FXCollections.observableArrayList();

        for (OrdinaryParticularAccount acc : BankAgency.getInstance().getOrdinaryAccountList()) {
            data.add(new ParticularAccountBean(acc));
        }

        particularAccountsTable.setItems(data);
    }

    @FXML
    protected void dashboardBtnOnClick() throws IOException {
        WindowsContextController.showDashboardOnPrincipalStage();
    }

    @FXML
    protected void carregarBtnOnClick() {
        refreshOrdinaryParticularAccountTable();
    }

    @FXML
    protected void removeBtnOnClick() {
        ParticularAccountBean acc = (ParticularAccountBean) particularAccountsTable.getSelectionModel().getSelectedItem();
        if (acc != null) {
            // TODO: Improve this
            for (OrdinaryParticularAccount account : BankAgency.getInstance().getOrdinaryAccountList()) {
                if (account.getAccountNumber().equals(acc.getId())) {
                    BankAgency.getInstance().getClientAccounts().remove(account);
                    break;
                }
            }
            refreshOrdinaryParticularAccountTable();
        } else {
            // TODO: Alert user that a line must be selected
        }
    }

    public static class ParticularAccountBean {
        private SimpleStringProperty id;
        private SimpleStringProperty nome;
        private SimpleStringProperty dono;
        private SimpleDoubleProperty balanco;
        private SimpleIntegerProperty cartoes;
        private SimpleStringProperty associado;
        private SimpleDoubleProperty valorDoJuro;

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
