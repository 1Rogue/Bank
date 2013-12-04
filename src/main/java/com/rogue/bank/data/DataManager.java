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
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Manages bank data
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class DataManager extends Observable {

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
        Runtime.getRuntime().addShutdownHook(new Thread() {
            
            @Override
            public void run() {
                saveAccounts();
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
     * Loads accounts from the bank file
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    private void loadAccounts() {
        File f = new File(this.bankLoc);
        FileReader fr = null;
        BufferedReader reader = null;
        try {
            fr = new FileReader(f);
            reader = new BufferedReader(fr);
            String inLine;
            while((inLine = reader.readLine()) != null) {
                String[] info = inLine.split(this.delimiter + "");
                if (info.length == 4) {
                    char type = info[1].charAt(0);
                    try {
                        int id = Integer.parseInt(info[0]);
                        int pin = Integer.parseInt(info[2]);
                        double bal = Double.parseDouble(info[3]);
                        Account temp = this.makeAccount(type, id, pin, bal);
                        this.accounts.put(temp.getID(), temp);
                    } catch (NumberFormatException ex) {
                        // error
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            // error
        } catch (IOException ex) {
            // error
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                // error
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
                writer.write(acc.formatWith(this.delimiter));
            }
        } catch (IOException ex) {
            // error
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                // error
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
    private Account makeAccount(char acc, int aid, int pin, double bal) {
        switch(acc) {
            case 's':
                return new SavingsAccount(aid, pin, bal);
            case 'x':
                return new CheckingAccount(aid, pin, bal);
            case 'c':
                return new CDAccount(aid, pin, bal);
        }
        return null;
    }

}
