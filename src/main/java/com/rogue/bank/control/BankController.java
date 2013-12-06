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
package com.rogue.bank.control;

import com.rogue.bank.Bank;
import com.rogue.bank.data.Account;

/**
 * Controls interactions for the Bank
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class BankController {
    
    private final Bank project;
    
    /**
     * {@link BankController} constructor
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param project The main {@link Bank} instance
     */
    public BankController(Bank project) {
        this.project = project;
    }
    
    /**
     * Validates a login with the provided {@link Account} id and pin. Will set
     * the session if it is valid.
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param id The id to check for
     * @param pin The pin to verify with
     * @return True if valid id and pin, false otherwise
     */
    public boolean validLogin(int id, int pin) {
        Account acc = this.project.getDataManager().getAccount(id);
        if (acc != null && acc.getPIN() == pin) {
            this.project.getSession().setAccount(acc);
            return true;
        }
        return false;
    }
    
    /**
     * Verifies the existance of an {@link Account}, but does not verify the pin
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param id The id to check for
     * @return True if exists, false otherwise
     */
    public boolean validAccount(int id) {
        Account acc = this.project.getDataManager().getAccount(id);
        return acc != null;
    }
    
    /**
     * Attempts to make a deposit to the current {@link Account} in use
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param amount The amount to deposit
     */
    public synchronized void deposit(double amount) {
        Account acc = this.project.getSession().getAccount();
        if (acc.canDeposit(amount)) {
            acc.deposit(amount);
        }
    }
    
    /**
     * Gets the balance of the current {@link Account}
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @return The current {@link Account} balance
     */
    public synchronized double getBalance() {
        return this.project.getSession().getAccount().getBalance();
    }
    
    /**
     * Attempts to withdraw a provided amount of money
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param amount The amount of money to withdraw
     * @return True if withdrawn, false if invalid amount is provided
     */
    public synchronized boolean withdraw(double amount) {
        Account acc = this.project.getSession().getAccount();
        if (acc.canWithdraw(amount)) {
            acc.withdraw(amount);
            return true;
        } else {
            return false;
        }
    }

}
