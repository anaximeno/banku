package com.groupnine.banku;

import java.time.LocalDateTime;

public abstract class BankingOperation {
    private final Operator operator;
    private final LocalDateTime dateTime;
    protected boolean wasExecuted;

    public BankingOperation(Operator operator, LocalDateTime dateTime) {
        this.operator = operator;
        this.dateTime = dateTime;
        wasExecuted = false;
    }

    public Operator getOperator() {
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
