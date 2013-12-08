/*
 * Copyright (C) 2013 Spencer Alderman
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.rogue.bank.data;

import com.rogue.bank.Bank;
import com.rogue.bank.control.BankController;
import com.rogue.bank.data.accounts.CDAccount;
import com.rogue.bank.data.accounts.CheckingAccount;
import com.rogue.bank.data.accounts.SavingsAccount;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages bank data
 *
 * @since 1.0.0
 * @author Spencer Alderman
 * @version 1.0.0
 */
public class DataManager {

    private final Bank project;
    private final String bankLoc;
    private final Map<Integer, Account> accounts = new HashMap<Integer, Account>();
    private final char delimiter = '\t';

    /**
     * DataManager constructor
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param project The {@link Bank} instance
     * @param bankFile String for bank file location
     */
    public DataManager(Bank project, String bankFile) {
        this.project = project;
        this.bankLoc = bankFile;
        this.loadAccounts();
        final Thread main = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                displayBankData("Final");
                saveAccounts();
                try {
                    main.join();
                } catch (InterruptedException ex) {
                    System.err.println("Fatal error: " + ex.getMessage());
                }
            }

        });
    }

    /**
     * Returns an {@link Account}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param id The account id
     * @return Account based on id, null if no account exists
     */
    public synchronized Account getAccount(int id) {
        return this.accounts.get(id);
    }

    /**
     * Returns a Collection of all loaded Accounts.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return A Collection of all loaded Accounts.
     */
    public synchronized Collection<Account> getAccounts() {
        return this.accounts.values();
    }

    /**
     * Loads accounts from the bank file
     *
     * @since 1.0.0
     * @version 1.0.0
     */
    private void loadAccounts() {
        File f = new File(this.bankLoc);
        FileReader fr = null;
        BufferedReader reader = null;
        BankController controller = this.project.getBankController();
        try {
            fr = new FileReader(f);
            reader = new BufferedReader(fr);
            String inLine;
            while ((inLine = reader.readLine()) != null) {
                String[] info = inLine.split(this.delimiter + "");
                if (info.length == 4) {
                    char type = info[1].charAt(0);
                    try {
                        int id = Integer.parseInt(info[0]);
                        int pin = Integer.parseInt(info[2]);
                        double bal = Double.parseDouble(info[3]);
                        Account temp = this.makeAccount(type, id, pin, bal);
                        temp.addObserver(controller);
                        this.registerAccount(temp);
                    } catch (NumberFormatException ex) {
                        // don't uses
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Error: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }

    /**
     * Saves accounts to the bank file
     *
     * @since 1.0.0
     * @version 1.0.0
     */
    private void saveAccounts() {
        File old = new File(this.bankLoc);
        old.renameTo(new File(this.bankLoc + ".old"));
        old = new File(this.bankLoc + ".old");
        old.deleteOnExit();
        File f = new File(this.bankLoc);
        if (f.exists()) {
            f.delete();
        }
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            f.createNewFile();
            fw = new FileWriter(f);
            writer = new BufferedWriter(fw);
            for (Account acc : this.accounts.values()) {
                writer.write(acc.formatWith(this.delimiter) + "\n");
            }
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }

    /**
     * Gets an account based on the char value
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param acc The char-value of the account
     * @param aid The account id
     * @param pin The account pin
     * @param bal The account balance
     * @return The appropriate {@link Account} type, null if none exists
     */
    public Account makeAccount(char acc, int aid, int pin, double bal) {
        switch (Character.toLowerCase(acc)) {
            case 's':
                return new SavingsAccount(aid, pin, bal);
            case 'x':
                return new CheckingAccount(aid, pin, bal);
            case 'c':
                return new CDAccount(aid, pin, bal);
        }
        return null;
    }

    /**
     * Registers an account to the bank.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param account The account to register
     * @return If the account was successfully registered
     */
    public boolean registerAccount(Account account) {
        if (this.accounts.containsKey(account.getID())) {
            return false;
        } else {
            this.accounts.put(account.getID(), account);
            return true;
        }
    }

    /**
     * Unregisters an account from the bank, deleting all known information
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param id The id of the account to remove
     * @return The removed account, null if non-existent
     */
    public Account unregisterAccount(int id) {
        return this.accounts.remove(id);
    }
    
    /**
     * Displays bank data.
     *
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param param The title of the bank data
     */
    public void displayBankData(String param) {
        System.out.println("========== " + param + " Bank Data ==================");
        System.out.println();
        System.out.println("Account Type    Account Balance");
        System.out.println("------------    ------- -----------");

        List<Account> accs = new ArrayList<Account>(this.project.getBankController().getAccounts());
        Collections.sort(accs);
        for (Account account : accs) {
            System.out.format("%-11s $   %7d $ %9.2f", account.getDisplayString(), account.getID(), account.getBalance());
            System.out.println();
        }

        System.out.println();
        System.out.println("===============================================");
        System.out.println();
    }
}
