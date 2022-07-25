package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;
import com.groupnine.banku.businesslogic.BankAgency;
import com.groupnine.banku.businesslogic.BankingOperation;
import com.groupnine.banku.miscellaneous.IDoubleFilter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovementsController {
    @FXML
    private Button dashboardButton;
    @FXML
    private Button operationsButton;
    @FXML
    private Button detailsButton;
    @FXML
    private Button refreshButton;

    @FXML
    private Text bankBalanceText;

    @FXML
    private DatePicker initDatePicker;
    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ListView<String> operationsListView;

    @FXML
    void initialize() {
        refreshMovements();

        dashboardButton.setOnMouseClicked(e -> BankuApp.getMainWindow().showDefaultView());

        operationsButton.setOnMouseClicked(e -> {
            BankuApp.getMainWindow().showView("views/operations-view.fxml", "Banku - Operations");
        });

        initDatePicker.setOnMouseExited(e -> refreshMovements());
        endDatePicker.setOnMouseExited(e -> refreshMovements());
    }

    void refreshMovements() {
        final BankAgency agency = BankAgency.getInstance();

        bankBalanceText.setText(String.valueOf(agency.getBankAccount().getBalance()));

        operationsListView.getItems().clear();

        IDoubleFilter<BankingOperation, LocalDate, LocalDate> filter = (input, init, end) -> {
            final boolean firstCondition = init == null || input.getDate().isAfter(init);
            final boolean secondCondition = end == null || input.getDate().isBefore(end);
            return firstCondition && secondCondition;
        };

        List<BankingOperation> operations = filterOperationsByDate(
                agency.getOperationsLog(), initDatePicker.getValue(), endDatePicker.getValue(), filter);

        for (BankingOperation operation : operations) {
            operationsListView.getItems().add(operation.getDescription());
        }
    }


    List<BankingOperation>
    filterOperationsByDate(List<BankingOperation> operations, LocalDate i, LocalDate e,
             IDoubleFilter<BankingOperation, LocalDate, LocalDate> filter)
    {
        List<BankingOperation> list = new ArrayList<>();

        for (BankingOperation op : operations) {
            if (filter.passesFilter(op, i, e))
                list.add(op);
        }

        return list;
    }
}
