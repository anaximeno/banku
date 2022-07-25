package com.groupnine.banku.businesslogic;

import java.time.LocalDate;

public abstract class BankingOperation {
    private final Employee operator;
    private final LocalDate dateTime;
    protected boolean wasExecuted;

    public BankingOperation(Employee operator, LocalDate dateTime) {
        this.operator = operator;
        this.dateTime = dateTime;
        wasExecuted = false;
    }

    public Employee getOperator() {
        return operator;
    }

    public LocalDate getDate() {
        return dateTime;
    }

    // um booleano para confirmar se uma operacao foi executada com sucesso
    public boolean operationWasExecuted() {
        return wasExecuted;
    }

    // obter um pequeno resumo sobre a operacao
    public abstract String getDescription();

    //obter toda informacao sobre a operacao
    public abstract String getFullDescription();

    // funcao que executa a operacao
    public abstract boolean executeOperation();

}
