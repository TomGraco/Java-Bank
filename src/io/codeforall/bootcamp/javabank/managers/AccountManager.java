package io.codeforall.bootcamp.javabank.managers;

import io.codeforall.bootcamp.javabank.domain.Customer;
import io.codeforall.bootcamp.javabank.domain.account.AccountType;
import io.codeforall.bootcamp.javabank.domain.account.CheckingAccount;
import io.codeforall.bootcamp.javabank.domain.account.SavingsAccount;
import io.codeforall.bootcamp.javabank.domain.account.Account;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for account management
 */
public class AccountManager {

    private static int numberAccounts = 0;
    private Map<Integer, Account> accountMap;

    /**
     * Creates a new {@code AccountManager}
     */
    public AccountManager() {
        this.accountMap = new HashMap<>();
    }

    /**
     * Creates a new {@link Account}
     *
     * @param accountType the account type
     * @return the new account
     */
    public Account openAccount(AccountType accountType) {

        Account newAccount;
        numberAccounts++;

        if (accountType == AccountType.CHECKING) {
            newAccount = new CheckingAccount(numberAccounts);

        } else {
            newAccount = new SavingsAccount(numberAccounts);
        }

        accountMap.put(newAccount.getId(), newAccount);
        return newAccount;
    }

    /**
     * Perform an {@link Account} deposit if possible
     *
     * @param id     the id of the account
     * @param amount the amount to deposit
     */
    public void deposit(int id, double amount) {
        accountMap.get(id).credit(amount);
    }

    /**
     * Perform an {@link Account} withdrawal if possible
     *
     * @param id     the id of the account
     * @param amount the amount to withdraw
     */
    public void withdraw(int id, double amount) {

        Account account = accountMap.get(id);

        if (!account.canWithdraw()) {
            System.out.println("\033[031;1mMinimun Balance Reached - Operation Denied\033[m");
            return;
        }

        accountMap.get(id).debit(amount);
    }

    /**
     * Performs a transfer between two {@link Account} if possible
     *
     * @param srcId  the source account id
     * @param dstId  the destination account id
     * @param amount the amount to transfer
     */
    public void transfer(int srcId, int dstId, double amount) {

        Account srcAccount = accountMap.get(srcId);
        Account dstAccount = accountMap.get(dstId);

        // make sure transaction can be performed
        if (srcAccount.canDebit(amount) && dstAccount.canCredit(amount)) {
            srcAccount.debit(amount);
            dstAccount.credit(amount);
            System.out.println("Transfer made successfully!");
            numberAccounts();
        } else {
            System.out.println("\033[031;1mOperation denied dued to Debit/Credit issues.\033[m");
        }
    }
    public void numberAccounts() {
        for (Account c : accountMap.values()) {
            System.out.println("\033[35;4mAccount ID:\033[m \033[36;1m" +c.getId() +
                    "\033[m - \033[35;4mBalance:\033[m\033[32;1m "+ c.getBalance() + "\033[m 💸");
        }
    }
}
