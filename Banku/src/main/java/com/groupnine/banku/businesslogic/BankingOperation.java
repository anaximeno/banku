package com.groupnine.banku.businesslogic;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class BankingOperation {
    private final Employee operator;
    private final LocalDateTime dateTime;
    protected boolean wasExecuted;

    public BankingOperation(Employee operator, LocalDateTime dateTime) {
        this.operator = operator;
        this.dateTime = dateTime;
        wasExecuted = false;
    }

    public Employee getOperator() {
        return operator;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // a boolean to confirm if an operation was successfully executed
    public boolean operationWasExecuted() {
        return wasExecuted;
    }

    //get a short detail about the operation
    public abstract String getDescription();

    //get full details about the operation
    public abstract String getFullDescription();

    //function that execute the operation
    public abstract boolean executeOperation();
}
