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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command for compiling interest and penalties to all accounts.
 *
 * @since 1.0.0
 * @author Robert Carmosino
 * @version 1.0.0
 */
public class ApplyInterestCommand implements BatchCommand {

    public boolean execute(BankController bankController, String[] args) {
        System.out.println();
        System.out.println("============== Interest Report ==============");
        System.out.println("Account Adjustment      New Balance");
        System.out.println("------- -----------     -----------");

        List<Account> accounts = new ArrayList<Account>(bankController.getAccounts());
        Collections.sort(accounts);
        for (Account account : accounts) {
            double prevBalance = account.getBalance();
            double newBalance = account.compileInterest();
            double diff = newBalance - prevBalance;
            System.out.format("%-7d $ %9.2f     $ %9.2f", account.getID(), diff, account.getBalance());
            System.out.println();
        }

        System.out.println("=============================================");
        System.out.println();
        return true;
    }

    public char getChar() {
        return 'a';
    }

}
