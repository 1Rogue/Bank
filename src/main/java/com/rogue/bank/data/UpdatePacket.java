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

import java.util.ArrayList;

/**
 * UpdatePacket for distinguishing types of Observer updates.
 * 
 * @since 1.0.0
 * @author CrypticStorm
 * @version 1.0.0
 */
public enum UpdatePacket {
    CREATE {
        public void update(ArrayList<String> accounts, Account account) {
            accounts.add(String.format("%7s %19s %9.2f", account.getID(), account.getDisplayString(), account.getBalance()));
        }
    },
    MODIFY {
        public void update(ArrayList<String> accounts, Account account) {
            String accID = String.valueOf(account.getID());
            for (int i = 0; i < accounts.size(); i++) {
                if (accounts.get(i).startsWith(accID)) {
                    accounts.set(i, String.format("%7s %19s %9.2f", account.getID(), account.getDisplayString(), account.getBalance()));
                    return;
                }
            }
        }
    },
    DELETE {
        public void update(ArrayList<String> accounts, Account account) {
            String accID = String.valueOf(account.getID());
            for (int i = 0; i < accounts.size(); i++) {
                if (accounts.get(i).startsWith(accID)) {
                    accounts.remove(i);
                    return;
                }
            }
        }
    };

    public abstract void update(ArrayList<String> accounts, Account account);
}
