package com.groupnine.banku.businesslogic;

import java.time.LocalDateTime;
import java.util.List;

public class AutomaticInterestHandler extends Thread {
    public static int minutesSinceTheLastCheck = 30;

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
        if (account.getBalanceRecord().size() >= minimunRecordDays) {
            IOperator operator = BankAgency.getInstance().getBankOperator("Automatic", "Thread", "multi-threading");
            // TODO: Update the interest applycation algorithm
            double value = account.getInterestRate() * getMean(account.getBalanceRecord());
            // TODO: Fic the problem with the usage of operator in this app!
            InterestApplication interestApplication = new InterestApplication((Employee) operator, LocalDateTime.now(), account, value);
            if (interestApplication.executeOperation()) {
                BankAgency.getInstance().addOperationLog(interestApplication);
                System.out.println("Interest Applied to account '" + account.getAccountNumber() + "'");
                // clear the list for new records
                account.getBalanceRecord().clear();
                // TODO: Alert user
            } else {
                // TODO: Error, Alert user
                System.out.println("Interest Could not be applied to account '" + account.getAccountNumber() + "'");
            }
        }
    }

    @Override
    public void run() {
        List<Account> accounts = BankAgency.getInstance().getClientAccounts();

        System.out.println("INFO: Interest handler started!");

        while (true) {
            for (Account acc : accounts) {
                if (acc instanceof OrdinaryParticularAccount) {
                    applyInterestsIntoAccount(acc, 30);
                    recordBalanceForAccount(acc);
                } else if (acc instanceof TemporaryParticularAccount) {
                    // TODO: Update record days according the specs
                    applyInterestsIntoAccount(acc, 12);
                    recordBalanceForAccount(acc);
                } else if (acc instanceof EnterpriseAccount) {
                    applyInterestsIntoAccount(acc, 30);
                    recordBalanceForAccount(acc);
                } else {
                    System.out.println("WARN: Unknown account type at AutomaticInterestHandler.");
                }
            }

            try {
                // Compute interest each half hour
                sleep(minutesSinceTheLastCheck * 60 * 1000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
