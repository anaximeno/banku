package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;
import com.groupnine.banku.businesslogic.*;
import com.groupnine.banku.miscellaneous.IBasicFilter;
import com.groupnine.banku.miscellaneous.ListUtils;
import com.groupnine.banku.miscellaneous.LogType;
import com.groupnine.banku.miscellaneous.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class ClientsController {
    private static ClientsController activeInstance;
    @FXML
    private Button reloadBtn;

    @FXML
    private TabPane tabPane;

    @FXML
    private TableView<ParticularAccountOwnerBean> particularAccountOwnerTable;
    @FXML
    private TableView<EnterpriseAccountOwnerBean> enterpriseAccountOwnerTable;

    @FXML
    private Tab tabParticulares;
    @FXML
    private Tab tabCorporativos;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField searchInput;

    private boolean particularAccountOwnerTableWasCreated = false;
    private boolean enterpriseAccountOwnerTableWasCreated = false;

    public static ClientsController getActiveInstance()
        /* Retorna a instancia ativa e inicialiada dessa view. */
    {
        return activeInstance;
    }

    @FXML
    protected void initialize()
    {
        createTables();
        refreshTables();

        tabPane.setOnMouseClicked(e -> refreshTables());
        reloadBtn.setOnMouseClicked(e -> refreshTables());

        comboBox.getItems().add("Nome");
        comboBox.getItems().add("Número");

        /* default */
        comboBox.setValue("Nome");
        comboBox.setOnMouseClicked(e -> refreshTables());

        searchInput.textProperty().addListener((obs, old, niu) -> refreshTables());

        /* Isto vai ser utilizado externamente para
         * atualizar o estado dessa view.
         * */
        activeInstance = this;
    }

    private void createTables()
        /* Cria todas as tabelas dessa view. */
    {
        createParticularAccountOwnerTable();
        createEnterpriseAccountOwnerTable();
    }

    public void refreshTables()
        /* Atualiza todas a tabelas. Este método é público pois
         * pode ser utilizado externamente. */
    {
        refreshParticularAccountOwnerTable();
        refreshEnterpriseAccountOwnerTable();
    }

    @FXML
    protected void dashboardBtnOnClick()
    {
        BankuApp.getMainWindow().showDefaultView();
    }

    @FXML
    protected void createButtonOnClick()
    {
        String viewPath = "", title = "Banku - ";

        if (tabParticulares.isSelected()) {
            viewPath = "views/add-new-particular-client-view.fxml";
            title += " Adicionar Novo Client Particular";
            AddNewParticularClientController.activeWindow = new WindowContextController(430, 615, viewPath, title);
            AddNewParticularClientController.activeWindow.showDefaultView();
        } else if (tabCorporativos.isSelected()) {
            viewPath = "views/add-new-enterprise-client-view.fxml";
            title += " Adicionar Novo Cliente Corporativo";
            AddNewEnterpriseClientController.activeWindow = new WindowContextController(435, 615, viewPath, title);
            AddNewEnterpriseClientController.activeWindow.showDefaultView();
        } else {
            Logger.log("Unknown tab state at createButtonOnClick in ClientsController", LogType.WARNING);
        }
    }

    @FXML
    protected void removeBtnOnClick()
    {
        if (tabParticulares.isSelected()) {
            removeSelectedClientFromTable(particularAccountOwnerTable);
        } else if (tabCorporativos.isSelected()) {
            removeSelectedClientFromTable(enterpriseAccountOwnerTable);
        } else {
            Logger.log("At removeBtnOnClick event, unknown tab state");
        }
        refreshTables();
    }

    static private
    <B extends AccountOwnerBean, A extends AccountOwner>
    void removeSelectedClientFromTable(TableView<B> table)
    {
        BankAgency agency = BankAgency.getInstance();

        B acc = (B) table.getSelectionModel().getSelectedItem();
        if (acc != null) {
            A account = (A) agency.findAccountOwnerByID(acc.getId());

            if (account != null) {
                String id = account.getOwnerID();

                agency.getClients().remove(account);

                Logger.log("Account Owner '" + id + "' was removed", LogType.INFO);
                // TODO: Notify user that account was removed?
            }
        } else {
            // TODO: Alert user that a line must be selected
            Logger.log("Remove button clicker with no line selected, nothing done");
        }
    }

    private<T extends AccountOwner> List<T>
    filterAccountOwnerList(List<T> clientList, String entry, IBasicFilter<T, String> filter)
    /* Filtra uma lista de contas, usando um método de filtragem fornecido pelo utilizador. */
    {
        List<T> list = new ArrayList<>();

        for (T item : clientList) {
            if (filter.passesFilter(item, entry)) {
                list.add(item);
            }
        }

        return list;
    }

    private<T extends AccountOwner>  List<T>
    applyFilter(List<T> accountOwnerList, String searchEntry)
    /* Retorna a lista filtrada. */
    {
        List<T> result = filterAccountOwnerList(accountOwnerList, searchEntry, (client, entry) -> {
            if (comboBox.getValue().equals("Nome")) {
                return client.getName().toLowerCase().contains(entry.toLowerCase());
            } else {
                return client.getOwnerID().contains(entry);
            }
        });
        result.sort((T a, T b) -> {
            if (comboBox.getValue().equals("Nome")) {
                return a.getName().compareTo(b.getName());
            } else {
                return a.getOwnerID().compareTo(b.getOwnerID());
            }
        });
        return result;
    }

    private void createParticularAccountOwnerTable()
    {
        particularAccountOwnerTable.getColumns().clear();
        particularAccountOwnerTable.setEditable(false);

        TableColumn<ClientsController.ParticularAccountOwnerBean, String> accId = new TableColumn<>("Id");
        TableColumn<ClientsController.ParticularAccountOwnerBean, String>  accName = new TableColumn<>("Nome");
        TableColumn<ClientsController.ParticularAccountOwnerBean, String>  accNIF = new TableColumn<>("NIF");
        TableColumn<ClientsController.ParticularAccountOwnerBean, String>  accAddress = new TableColumn<>("Endereco");
        TableColumn<ClientsController.ParticularAccountOwnerBean, String>  accNationality = new TableColumn<>("Nacionalidade");
        TableColumn<ClientsController.ParticularAccountOwnerBean, Integer>  accNAccount = new TableColumn<>("#Contas");

        accId.setCellValueFactory(new PropertyValueFactory<>("id"));
        accName.setCellValueFactory(new PropertyValueFactory<>("name"));
        accNIF.setCellValueFactory(new PropertyValueFactory<>("NIF"));
        accAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        accNationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        accNAccount.setCellValueFactory(new PropertyValueFactory<>("accountNum"));

        particularAccountOwnerTable.getColumns().addAll(accId, accName, accNIF, accAddress, accNationality, accNAccount);
        particularAccountOwnerTableWasCreated = true;
    }

    private void createEnterpriseAccountOwnerTable()
    {
        enterpriseAccountOwnerTable.getColumns().clear();
        enterpriseAccountOwnerTable.setEditable(false);

        TableColumn<ClientsController.EnterpriseAccountOwnerBean, String> accId = new TableColumn<>("Id");
        TableColumn<ClientsController.EnterpriseAccountOwnerBean, String>  accName = new TableColumn<>("Nome");
        TableColumn<ClientsController.EnterpriseAccountOwnerBean, String>  accNIF = new TableColumn<>("NIF");
        TableColumn<ClientsController.EnterpriseAccountOwnerBean, String>  accAddress = new TableColumn<>("Endereco");
        TableColumn<ClientsController.EnterpriseAccountOwnerBean, Integer>  accNPartners = new TableColumn<>("Parceiros");
        TableColumn<ClientsController.EnterpriseAccountOwnerBean, Integer>  accNAccount = new TableColumn<>("#Contas");

        accId.setCellValueFactory(new PropertyValueFactory<>("id"));
        accName.setCellValueFactory(new PropertyValueFactory<>("name"));
        accNIF.setCellValueFactory(new PropertyValueFactory<>("NIF"));
        accAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        accNPartners.setCellValueFactory(new PropertyValueFactory<>("partnersNum"));
        accNAccount.setCellValueFactory(new PropertyValueFactory<>("accountsNum"));

        enterpriseAccountOwnerTable.getColumns().addAll(accId, accName, accNIF, accAddress, accNPartners, accNAccount);
        enterpriseAccountOwnerTableWasCreated = true;
    }

    private void refreshParticularAccountOwnerTable()
    {
        if (!particularAccountOwnerTableWasCreated) {
            createParticularAccountOwnerTable();
        }

        List<ParticularAccountOwner> ParticularOwnersList = BankAgency.getInstance().getParticularClientList();

        String searchEntry = searchInput.getText();

        if (searchEntry != null && !searchEntry.isEmpty()) {
            ParticularOwnersList = applyFilter(ParticularOwnersList, searchEntry);
        }

        ObservableList<ClientsController.ParticularAccountOwnerBean> data = FXCollections.observableArrayList();

        for (ParticularAccountOwner accOwner : ParticularOwnersList) {
            data.add(new ClientsController.ParticularAccountOwnerBean(accOwner));
        }

        particularAccountOwnerTable.setItems(data);
    }

    private void refreshEnterpriseAccountOwnerTable()
    {
        if (!enterpriseAccountOwnerTableWasCreated) {
            createEnterpriseAccountOwnerTable();
        }

        List<EnterpriseAccountOwner> enterpriseOwnersList = BankAgency.getInstance().getEnterpriseClientList();

        String searchEntry = searchInput.getText();

        if (searchEntry != null && !searchEntry.isEmpty()) {
            enterpriseOwnersList = applyFilter(enterpriseOwnersList, searchEntry);
        }


        ObservableList<ClientsController.EnterpriseAccountOwnerBean> data = FXCollections.observableArrayList();

        for (EnterpriseAccountOwner accOwner : enterpriseOwnersList) {
            data.add(new ClientsController.EnterpriseAccountOwnerBean(accOwner));
        }

        enterpriseAccountOwnerTable.setItems(data);
    }

    public static abstract class AccountOwnerBean {
        final private SimpleStringProperty id;
        final private SimpleStringProperty name;
        final private SimpleStringProperty NIF;
        final private SimpleStringProperty address;

        public AccountOwnerBean(final AccountOwner accountowner) {
            this.id = new SimpleStringProperty(accountowner.getOwnerID());
            this.name = new SimpleStringProperty(accountowner.getName());
            this.NIF = new SimpleStringProperty(accountowner.getNIF());
            this.address = new SimpleStringProperty(accountowner.getAddress());
        }

        public String getId() {
            return id.get();
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getNIF() {
            return NIF.get();
        }

        public void setNIF(String NIF) {
            this.NIF.set(NIF);
        }

        public String getAddress() {
            return address.get();
        }

        public void setAddress(String address) {
            this.address.set(address);
        }
    }

    public static class ParticularAccountOwnerBean extends ClientsController.AccountOwnerBean {

        final private SimpleStringProperty nationality;
        final private SimpleIntegerProperty accountNum;

        public ParticularAccountOwnerBean(final ParticularAccountOwner particularAccountOwner) {
            super((AccountOwner) particularAccountOwner);
            this.nationality = new SimpleStringProperty(particularAccountOwner.getNationality());
            this.accountNum = new SimpleIntegerProperty(ListUtils.lengthOf(particularAccountOwner.getAccounts()));
        }

        public String getNationality() {
            return nationality.get();
        }

        public void setNationality(String nationality) {
            this.nationality.set(nationality);
        }

        public int getAccountNum() {
            return accountNum.get();
        }

        public void setAccountNum(int accountNum) {
            this.accountNum.set(accountNum);
        }
    }

    public static class EnterpriseAccountOwnerBean extends ClientsController.AccountOwnerBean {
        final private SimpleIntegerProperty partnersNum;
        final private SimpleIntegerProperty accountsNum;

        EnterpriseAccountOwnerBean(final EnterpriseAccountOwner enterpriseAccountOwner) {
            super((AccountOwner) enterpriseAccountOwner);
            this.partnersNum = new SimpleIntegerProperty(ListUtils.lengthOf(enterpriseAccountOwner.getPartners()));
            this.accountsNum = new SimpleIntegerProperty(ListUtils.lengthOf(enterpriseAccountOwner.getAccounts()));
        }

        public int getPartnersNum() {
            return partnersNum.get();
        }

        public void setPartnersNum(int partnersNum) {
            this.partnersNum.set(partnersNum);
        }

        public int getAccountsNum() {
            return accountsNum.get();
        }

        public void setAccountsNum(int accountsNum) {
            this.accountsNum.set(accountsNum);
        }
    }
}
