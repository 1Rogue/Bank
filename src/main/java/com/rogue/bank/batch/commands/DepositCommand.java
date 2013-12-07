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
package com.rogue.bank.batch.commands;

import com.rogue.bank.batch.BatchCommand;
import com.rogue.bank.control.BankController;
import com.rogue.bank.data.Account;

/**
 * Command for depositing into an account.
 * 
 * @since 1.0.0
 * @author Robert Carmosino
 * @version 1.0.0
 */
public class DepositCommand implements BatchCommand {

    public boolean execute(BankController bankController, String[] args) {
        try {
            int id = Integer.valueOf(args[0]);
            double amount = Double.valueOf(args[1]);
            Account account = bankController.getAccount(id);
            if (account != null && account.canDeposit(amount)) {
                account.deposit(amount);
                System.out.format("%-7d d       $ %9.2f     $ %9.2f", id, amount, account.getBalance());
            } else {
                System.out.format("%-7d d       $ %9.2f     Failed", id, amount);
            }
            System.out.println();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public char getChar() {
        return 'd';
    }

}
