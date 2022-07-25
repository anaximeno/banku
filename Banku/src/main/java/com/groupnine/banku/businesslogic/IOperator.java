package com.groupnine.banku.businesslogic;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IOperator {
    public boolean makeTransaction(Account from, Account to, double value, LocalDate date, String userDescription);
    public boolean makeTransaction(Account from, Account to, double value);
    public boolean makeMoneyWithdraw(Account from, double value);
    public boolean makeMoneyDeposit(Account account, double value);
    public boolean addNewAccountToTheBank(Account account);
    public boolean addNewClientToTheBank(AccountOwner client);
    public boolean deleteAccount(String accountNumber);
    public boolean deleteAccountOwner(String ownerID);
}
