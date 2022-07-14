package com.groupnine.banku.businesslogic;

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

    public boolean operationWasExecuted() {
        return wasExecuted;
    }

    public abstract String getDescription();

    public abstract String getFullDescription();

    public abstract boolean executeOperation();
}
