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

import java.util.Observable;

/**
 * Account superclass
 *
 * @since 1.0.0
 * @author 1Rogue
 * @author CrypticStorm
 * @version 1.0.0
 */
public abstract class Account extends Observable {

    protected final int id;
    protected final int pin;
    protected double balance;

    /**
     * Account constructor
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param id Account id number
     * @param pin Account pin number (4 digits)
     * @param balance Account balance (Non-negative)
     */
    protected Account(final int id, final int pin, double balance) {
        this.id = id;
        this.pin = pin;
        this.balance = balance;
    }

    /**
     * Returns the Account ID number.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return Account ID number
     */
    public final int getID() {
        return this.id;
    }

    /**
     * Returns the Account PIN number.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return Account PIN number
     */
    public final int getPIN() {
        return this.pin;
    }

    /**
     * Returns the Account balance.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return Account balance
     */
    public final double getBalance() {
        return this.balance;
    }

    /**
     * Return if this account can deposit the given amount.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param amount The amount to deposit.
     * @return If this account can deposit the given amount.
     */
    public boolean canDeposit(final double amount) {
        return amount >= 0;
    }

    /**
     * Deposits a given value into an account.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param amount The amount to deposit.
     * @return The new account balance.
     */
    public final double deposit(final double amount) {
        this.balance += amount;
        this.setChanged();
        this.notifyObservers(this.balance);
        return this.balance;
    }

    /**
     * Return if this account can withdraw the given amount.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param amount The amount to withdraw.
     * @return If this account can withdraw the given amount.
     */
    public boolean canWithdraw(final double amount) {
        return amount >= 0 && amount <= this.balance;
    }

    /**
     * WIthdraws a given value from an account.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param amount The amount to withdraw.
     * @return The new account balance.
     */
    public final double withdraw(final double amount) {
        this.balance -= amount;
        this.setChanged();
        this.notifyObservers(this.balance);
        return this.balance;
    }

    /**
     * Returns a char representing the account type. Defined by individual
     * account type.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return char representation of the account type
     */
    public abstract char getDisplayCharacter();

    /**
     * Returns a String representing the account type. Defined by individual
     * account type.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return String representation of the account type
     */
    public abstract String getDisplayString();

    /**
     * Returns the minimum balance needed to open an account. Defined by
     * individual account type.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return minimum balance of the Account
     */
    public abstract double getMinimumBalance();

    /**
     * Returns the monthly interest rate for an account. Defined by individual
     * account type.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return the monthly interest rate.
     */
    public abstract double getMonthlyInterestRate();

    /**
     * Compiles interest on an account. Responsible for monthly fees.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return the new account balance.
     */
    public double compileInterest() {
        this.balance += this.balance * this.getMonthlyInterestRate();
        this.setChanged();
        this.notifyObservers(this.balance);
        return this.balance;
    }

    /**
     * Formats the account information using a delimiter
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param delimiter The delimiter to use
     * @return A formatted string of the information
     */
    public String formatWith(char delimiter) {
        return new StringBuilder()
                .append(this.getID()).append(delimiter)
                .append(this.getDisplayCharacter()).append(delimiter)
                .append(this.getPIN()).append(delimiter)
                .append(this.getBalance()).toString();
    }
}
