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
package com.rogue.bank.data.accounts;

import com.rogue.bank.data.Account;

/**
 * CheckingAccount implementation.
 *
 * @since 1.0.0
 * @author CrypticStorm
 * @version 1.0.0
 */
public final class SavingsAccount extends Account {

    /**
     * SavingsAccount constructor
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param id Account id number
     * @param pin Account pin number (4 digits)
     * @param balance Account balance (Non-negative)
     */
    public SavingsAccount(final int id, final int pin, final double balance) {
        super(id, pin, balance);
    }

    @Override
    public char getDisplayCharacter() {
        return 's';
    }

    @Override
    public String getDisplayString() {
        return "Savings";
    }

    @Override
    public double getMinimumBalance() {
        return 200.0;
    }

    @Override
    public double getMonthlyInterestRate() {
        return 0.005 / 12;
    }

    @Override
    public double compileInterest() {
        if (this.balance < this.getMinimumBalance()) {
            return this.balance -= this.balance > 10.0 ? 10.0 : 0.1 * this.balance;
        } else {
            return super.compileInterest();
        }
    }
}
