package com.groupnine.banku.businesslogic;

import com.groupnine.banku.BankuApp;
import com.groupnine.banku.controllers.AccountsController;
import com.groupnine.banku.miscellaneous.LogType;
import com.groupnine.banku.miscellaneous.Logger;
import com.groupnine.banku.miscellaneous.ListUtils;

import java.time.LocalDateTime;
import java.util.List;

public class AutomaticInterestHandler extends Thread {
    public static int minutesSinceTheLastCheck = 30;

    /* Um ano de balanços salvos necessário para aplicar juros em contas
     * Particulares e de Empresas.
     * */
    public static final int yearlyInterstApplicationRecordDays = 12 * 30;

    public void recordBalanceForAccount(Account account)
    /* Guarda o balanço de uma conta, chamando o método
     * responsável por isso dentro da classe.
     * */
    {
        account.recordCurrentBalance();
    }

    public double calculateInterests(Account account)
    /* Retorna o valor do juro da conta.
     * */
    {
        final double meanRecordedBalances = ListUtils.meanOf(account.getBalanceRecord());
        return account.getInterestRate() * meanRecordedBalances;
    }

    public void applyInterestsIntoAccount(Account account, int minimunRecordDays)
    /* Aplica o juro numa conta, se essa tiver ao menos `minimumRecordDays` dias de saldo
     * guardado ao longo do tempo.
     * */
    {
        assert minimunRecordDays > 0; // At least 1 day of record is requested
        assert account != null; // Account should not be null

        BankAgency agency = BankAgency.getInstance();

        int numberOfDailyRecords = ListUtils.lengthOf(account.getBalanceRecord());

        if (numberOfDailyRecords >= minimunRecordDays) {
            IOperator operator = agency.getBankOperator("BackgroundThread", "multi-threading");

            final double interestValue = calculateInterests(account);

            InterestApplication interestApplication = new InterestApplication(
                    (Employee) operator, LocalDateTime.now(), account, interestValue);

            if (interestApplication.executeOperation()) {
                agency.addOperationLog(interestApplication);

                Logger.log("Interest Applied to account '" + account.getAccountNumber() + "'", LogType.INFO);
ye
                // clear the list for new records
                account.getBalanceRecord().clear();

                /* Mostra a mudança na view de conta ativa, caso ela tenha sido criada. */
                if (AccountsController.getActiveInstance() != null)
                    AccountsController.getActiveInstance().refreshTables();
            }
            else {
                Logger.log("Interest Could not be applied to account '" + account.getAccountNumber() + "'", LogType.ERROR);
            }
        }
    }

    @Override
    public void run()
    /* Executa o InterestHandler. */
    {
        List<Account> accounts = BankAgency.getInstance().getClientAccounts();

        Logger.log("Interest handler started in background", LogType.INFO);

        while (BankuApp.getMainWindow().getStage().isShowing()) {
            for (Account acc : accounts) {
                recordBalanceForAccount(acc);

                if (acc instanceof OrdinaryParticularAccount || acc instanceof EnterpriseAccount) {
                    applyInterestsIntoAccount(acc, yearlyInterstApplicationRecordDays);
                }
                else if (acc instanceof TemporaryParticularAccount tAcc) {
                    applyInterestsIntoAccount(tAcc, (int) tAcc.getNumberOfDaysBetweenCreationAndExpiration());
                }
                else {
                    Logger.log("Unknown account type at AutomaticInterestHandler", LogType.WARNING);
                }
            }

            try {
                // Compute interest each half hour
                sleep((long) minutesSinceTheLastCheck * 60 * 1000);
            } catch (InterruptedException exception) {
                Logger.log(exception.getMessage(), LogType.WARNING);
            }
        }

        Logger.log("Interest handler stopped.", LogType.INFO);
    }
}
