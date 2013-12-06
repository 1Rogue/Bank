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
     * Validates a login with the provided account id and pin
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
        return acc != null && acc.getPIN() == pin;
    }

}
