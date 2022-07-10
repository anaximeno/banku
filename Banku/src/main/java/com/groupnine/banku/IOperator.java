package com.groupnine.banku;

import java.time.LocalDateTime;

public interface IOperator {
    public boolean makeTransaction(Account from, Account to, double value);
    public boolean makeMoneyWithdraw(Account from, double value);
    public boolean makeMoneyDeposit(Account account, double value);
    public boolean addClientAccountToTheBank(EnterpriseAccount account);
    public boolean deleteAccount(String accountNumber);
}
