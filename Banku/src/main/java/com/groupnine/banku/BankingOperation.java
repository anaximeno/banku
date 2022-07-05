package com.groupnine.banku;

import java.time.LocalDateTime;

public abstract class BankingOperation {
    final Operator operator;
    final LocalDateTime dateTime;

    public BankingOperation(Operator operator, LocalDateTime dateTime) {
        this.operator = operator;
        this.dateTime = dateTime;
    }

    public Operator getOperator() {
        return operator;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public abstract String getDescription();

    public abstract String getFullDescription ();
}
