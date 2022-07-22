package com.groupnine.banku.businesslogic;

import java.time.LocalDateTime;

public class Transaction extends BankingOperation{

    private Account accountFrom;
    private Account accountTo;
    private double value;
    private double balanceOfFromAccountBeforeTransaction;
    private double balanceOfFromAccountAfterTransaction;
    private double balanceOfToAccountBeforeTransaction;
    private double balanceOfToAfterTransaction;
    private String details;

    public Transaction(Employee operator, LocalDateTime dateTime, Account accountFrom, Account accountTo, double value) {
        super(operator, dateTime);
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.value = value;

    }

    @Override
    public String getDescription () {
        // If transaction interest was applied, show the information
        String interestInfo = accountFrom instanceof EnterpriseAccount ?
                "Com os juros de trandferencia de " + value * EnterpriseAccount.TRANSACTION_INTEREST + "." :  "";
        String description = "Dinheiro transferido da conta numero " + accountFrom.getAccountNumber()
                + " para a conta numero " + accountTo.getAccountNumber() + " no valor de " + value + "."
                + interestInfo;
         return description;
    }

    @Override
    public String getFullDescription (){
        String fullDescription = getDescription()
                + "\n Saldo da conta debitada antes da transferencia = " + balanceOfFromAccountBeforeTransaction
                + "\n Saldo da conta debitada depois da transferencia = " + balanceOfFromAccountAfterTransaction
                + "\n Saldo da conta creditada antes da transferencia = " + balanceOfToAccountBeforeTransaction
                + "\n Saldo da conta creditada depois da transferencia = " + balanceOfToAfterTransaction;
        return fullDescription;
    }

    @Override
    public boolean executeOperation() {
        BankAgency agency = BankAgency.getInstance();
        if (accountFrom != null && accountTo != null && accountFrom.getAccountBalance() - value > 0 && !wasExecuted) {
            // guardar o saldo antes da transacao
            this.balanceOfFromAccountBeforeTransaction = accountFrom.getAccountBalance();
            this.balanceOfToAccountBeforeTransaction = accountTo.getAccountBalance();

            if (accountFrom instanceof EnterpriseAccount) {
                // Se o tipo de conta for enterprise e aplicado uma taxa.
                double interestValue = EnterpriseAccount.TRANSACTION_INTEREST * accountFrom.getAccountBalance();
                InterestApplication interestApplication = new InterestApplication(
                        getOperator(), LocalDateTime.now(), accountFrom, interestValue
                );
                interestApplication.executeOperation();
                BankAgency.getInstance().addOperationLog(interestApplication);
            }

            accountFrom.setAccountBalance(accountFrom.getAccountBalance() - value);
            accountTo.setAccountBalance(accountTo.getAccountBalance() + value);

            // guardar o saldo depois da transacao
            this.balanceOfFromAccountAfterTransaction = accountFrom.getAccountBalance();
            this.balanceOfToAfterTransaction = accountTo.getAccountBalance();

            wasExecuted = true;
            return true;
        } else {
            return false;
        }
    }
}
