package com.groupnine.banku.businesslogic;

import com.groupnine.banku.miscellaneous.Misc;
import com.groupnine.banku.miscellaneous.ListUtils;
import com.groupnine.banku.controllers.WindowsContextController;

import java.time.LocalDateTime;
import java.util.List;

public class AutomaticInterestHandler extends Thread {
    public static int minutesSinceTheLastCheck = 1;

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
            IOperator operator = agency.getBankOperator("Background", "Thread", "multi-threading");

            final double interestValue = calculateInterests(account);

            InterestApplication interestApplication = new InterestApplication(
                    (Employee) operator, LocalDateTime.now(), account, interestValue);

            if (interestApplication.executeOperation()) {
                agency.addOperationLog(interestApplication);

                Misc.log("Interest Applied to account '" + account.getAccountNumber() + "'", Misc.LogType.INFO);

                // clear the list for new records
                account.getBalanceRecord().clear();
            }
            else {
                Misc.log("Interest Could not be applied to account '" + account.getAccountNumber() + "'", Misc.LogType.ERROR);
            }
        }
    }

    @Override
    public void run() {
        List<Account> accounts = BankAgency.getInstance().getClientAccounts();

        Misc.log("Interest handler started in background", Misc.LogType.INFO);

        while (WindowsContextController.getPrincipalStage().isShowing()) {
            for (Account acc : accounts) {
                recordBalanceForAccount(acc);

                if (acc instanceof OrdinaryParticularAccount || acc instanceof EnterpriseAccount) {
                    applyInterestsIntoAccount(acc, yearlyInterstApplicationRecordDays);
                }
                else if (acc instanceof TemporaryParticularAccount tAcc) {
                    applyInterestsIntoAccount(tAcc, tAcc.getNumberOfDaysBetweenCreationAndExpiration());
                }
                else {
                    Misc.log("Unknown account type at AutomaticInterestHandler", Misc.LogType.WARNING);
                }
            }

            try {
                // Compute interest each half hour
                sleep(minutesSinceTheLastCheck * 60 * 1000);
            } catch (InterruptedException exception) {
                Misc.log(exception.getMessage(), Misc.LogType.WARNING);
            }
        }
    }
}
