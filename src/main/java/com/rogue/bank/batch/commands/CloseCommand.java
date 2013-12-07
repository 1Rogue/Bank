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
 * Command for closing an account.
 * 
 * @since 1.0.0
 * @author Robert Carmosino
 * @version 1.0.0
 */
public class CloseCommand implements BatchCommand {

    public boolean execute(BankController bankController, String[] args) {
        try {
            int id = Integer.valueOf(args[0]);
            Account account = bankController.deleteAccount(id);
            if (account != null) {
                System.out.format("%-7d c       Closed: Success $ %9.2f", id, account.getBalance());
            } else {
                System.out.format("%-7d o       Closed: Failed", id);
            }
            System.out.println();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public char getChar() {
        return 'c';
    }

}
