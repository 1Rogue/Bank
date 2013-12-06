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
package com.rogue.bank;

import com.rogue.bank.data.Account;

/**
 * Simple storage of a currently managed session
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class Session {
    
    private Account acc;
    
    /**
     * Sets the current session's {@link Account}
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param acc The {@link Account} to set
     */
    public void setAccount(Account acc) {
        this.acc = acc;
    }
    
    /**
     * Returns the current session's {@link Account}, null if no account is signed in
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @return The current session's {@link Account}. null if no account is signed in
     */
    public Account getAccount() {
        return this.acc;
    }

}
