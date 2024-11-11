package io.codeforall.bootcamp.javabank;

import io.codeforall.bootcamp.javabank.domain.Bank;
import io.codeforall.bootcamp.javabank.domain.Customer;
import io.codeforall.bootcamp.javabank.domain.account.Account;
import io.codeforall.bootcamp.javabank.domain.account.AccountType;
import io.codeforall.bootcamp.javabank.domain.account.SavingsAccount;
import io.codeforall.bootcamp.javabank.managers.AccountManager;
import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerInputScanner;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.precisiondouble.DoubleInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

public class BankUI {
    public static void main(String[] args) {
        Prompt prompt = new Prompt(System.in, System.out);
        Customer user = new Customer("Tomás", 1);
        Customer user1 = new Customer("Rui", 2);
        AccountManager manager = new AccountManager();
        Bank bank = new Bank(manager);

        bank.addCustomer(user);
        bank.addCustomer(user1);


        IntegerInputScanner id = new IntegerInputScanner();
        id.setMessage("Insert Customer \033[36;1mID: \033[m");
        int userID = prompt.getUserInput(id);

        Customer currentClient = bank.findClient(userID);
        System.out.println("\nWelcome Sr/Sra " + "\033[33;1m" + currentClient.getName() + "\033[m" + ", your current ID is " + "\033[36;1m" + userID + "\033[m.");


        String[] options = {
                "View Balance",
                "Deposit",
                "Withdraw",
                "Transfer",
                "New Acount",
                "\033[031;1mQuit\033[m"
        };
        MenuInputScanner bankMenu = new MenuInputScanner(options);
        bankMenu.setMessage("⏬ \033[34;1mAvailable options\033[m ⏬");
        int choice = 0;

        while (choice != 6) {
            choice = prompt.getUserInput(bankMenu);

            if (choice == 1) {
                System.out.println("\nClient: Sr/Sra \033[33;1m" + currentClient.getName() + "\033[m");
                manager.numberAccounts();
                System.out.println("\nTotal balance: " + "\033[33;1m" + currentClient.getBalance() + "\033[m 💸");

            } else if (choice == 2) {
                System.out.println("\n⏬ \033[34;1mYour Accounts: ⏬\033[m\n");
                manager.numberAccounts();

                DoubleInputScanner askAmount = new DoubleInputScanner();
                askAmount.setMessage("\nDeposit amount: ");
                double amount = prompt.getUserInput(askAmount);

                IntegerInputScanner acountid = new IntegerInputScanner();
                acountid.setMessage("Acount \033[36;1mID: \033[m");
                int userAcount = prompt.getUserInput(acountid);

                if (currentClient.validate(userAcount)) {
                    manager.deposit(userAcount, amount);
                    System.out.println("\nDeposit of \033[36;1m" + amount + "\033[m💸, sucess!");
                    System.out.println("\nAcount Owner: \033[33;1m" + currentClient.getName() + "\033[m\n" +
                            "Current Balance: \033[32;1m" + currentClient.getBalance(userAcount) +
                            "\033[m 💸\nAcount ID: \033[36;1m" + userAcount + "\033[m");
                } else {
                    System.out.println("\033[031;1mAcess Error\033[m");
                }

            } else if (choice == 3) {

                System.out.println("\n⏬ \033[34;1mYour Accounts: ⏬\033[m\n");
                manager.numberAccounts();

                DoubleInputScanner askAmount = new DoubleInputScanner();
                askAmount.setMessage("\nWithdraw amount: ");
                double amount = prompt.getUserInput(askAmount);

                IntegerInputScanner acountid = new IntegerInputScanner();
                acountid.setMessage("Acount \033[36;1mID: \033[m");
                int withdrawUser = prompt.getUserInput(acountid);

                if (currentClient.validate(withdrawUser)) {
                    manager.withdraw(withdrawUser, amount);
                    System.out.println("\nAcount Owner: \033[33;1m" + currentClient.getName() + "\033[m\n" +
                            "Current Balance: \033[32;1m" + currentClient.getBalance(withdrawUser) +
                            "\033[m\nAcount ID: \033[36;1m" + withdrawUser + "\033[m 💸");
                } else {
                    System.out.println("\033[031;1mAcess Error\033[m");
                }
            } else if (choice == 4) {

                System.out.println("\n⏬ \033[34;1mYour Accounts: ⏬\033[m\n");
                manager.numberAccounts();
                System.out.println("\n⏬ \033[34;1mTransfer Process: ⏬\033[m");

                IntegerInputScanner originID= new IntegerInputScanner();
                originID.setMessage("\nOrigin Acount \033[36;1mID: \033[m");
                int ogID = prompt.getUserInput(originID);

                IntegerInputScanner masterID = new IntegerInputScanner();
                masterID.setMessage("Master Acount \033[36;1mID: \033[m");
                int masID = prompt.getUserInput(masterID);

                DoubleInputScanner askAmount = new DoubleInputScanner();
                askAmount.setMessage("\nTransfer amount: ");
                double amount = prompt.getUserInput(askAmount);

                if (currentClient.validate(ogID)&&currentClient.validate(masID)) {
                    manager.transfer(ogID,masID,amount);

                } else {
                    System.out.println("\033[031;1mTransfer Error\033[m");
                }
            } else if (choice == 5) {
                String[] types = {
                        "\033[35;1mSavings\033[m",
                        "\033[35;1mChecking\033[m",
                };
                MenuInputScanner typeMenu = new MenuInputScanner(types);
                typeMenu.setMessage("⏬ \033[34;1mAvailable types of account\033[m ⏬");
                typeMenu.setError("\033[031;1mInvalid type...\033[m");
                int type = prompt.getUserInput(typeMenu);

                if (type == 1) {
                    int id1 = currentClient.openAccount(AccountType.SAVINGS);
                    System.out.println("\033[33;1m" + currentClient.getName() + "\033[m" + " has created a new Savings account with the ID: " + id1);
                } else if (type == 2) {
                    int id2 = currentClient.openAccount(AccountType.CHECKING);
                    System.out.println("\033[33;1m" + currentClient.getName() + "\033[m" + " has created a new Checking account with the ID: " + id2);
                }

            } else {
                System.out.println("\033[031;1mExiting...\033[m");
            }

        }
    }
}
