package com.groupnine.banku.businesslogic;

import com.groupnine.banku.Misc;
import com.groupnine.banku.controllers.WindowsContextController;

import java.time.LocalDateTime;
import java.util.List;

public class AutomaticInterestHandler extends Thread {
    public static int minutesSinceTheLastCheck = 1;

    public void recordBalanceForAccount(Account account) {
        account.recordCurrentBalance();
    }

    public double getMean(List<Double> list) {
        double mean = 0;

        for (Double value : list) {
            mean += value / list.size();
        }

        return mean;
    }

    public void applyInterestsIntoAccount(Account account, int minimunRecordDays) {
        // Thirty days of record, then apply interests
        int numberOfDailyRecords = account.getBalanceRecord() != null ? account.getBalanceRecord().size() : 0;
        if (numberOfDailyRecords >= minimunRecordDays) {
            IOperator operator = BankAgency.getInstance().getBankOperator("Background", "Thread", "multi-threading");
            // TODO: Update the interest applycation algorithm
            double value = account.getInterestRate() * getMean(account.getBalanceRecord());
            // TODO: Fic the problem with the usage of operator in this app!
            InterestApplication interestApplication = new InterestApplication((Employee) operator, LocalDateTime.now(), account, value);
            if (interestApplication.executeOperation()) {
                BankAgency.getInstance().addOperationLog(interestApplication);
                Misc.log("Interest Applied to account '" + account.getAccountNumber() + "'", Misc.LogType.INFO);
                // clear the list for new records
                account.getBalanceRecord().clear();
                // TODO: notify user
            } else {
                Misc.log("Interest Could not be applied to account '" + account.getAccountNumber() + "'", Misc.LogType.ERROR);
                // TODO: Error, notify user
            }
        }
    }

    @Override
    public void run() {
        List<Account> accounts = BankAgency.getInstance().getClientAccounts();

        Misc.log("Interest handler started in background", Misc.LogType.INFO);

        while (WindowsContextController.getPrincipalStage().isShowing()) {
            for (Account acc : accounts) {
                if (acc instanceof OrdinaryParticularAccount) {
                    applyInterestsIntoAccount(acc, 1);
                    recordBalanceForAccount(acc);
                } else if (acc instanceof TemporaryParticularAccount) {
                    // TODO: Update record days according the specs
                    applyInterestsIntoAccount(acc, 1);
                    recordBalanceForAccount(acc);
                } else if (acc instanceof EnterpriseAccount) {
                    applyInterestsIntoAccount(acc, 1);
                    recordBalanceForAccount(acc);
                } else {
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
