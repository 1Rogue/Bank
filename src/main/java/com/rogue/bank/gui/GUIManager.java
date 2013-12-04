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
package com.rogue.bank.gui;

import com.rogue.bank.Bank;
import java.util.Observable;
import java.util.Observer;

/**
 * Manages GUI for {@link Bank}
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class GUIManager implements Observer {

    private final Bank project;

    /**
     * {@link GUIManager} constructor
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param project The main {@link Bank} instance
     */
    public GUIManager(Bank project) {
        this.project = project;
    }

    /**
     * Handles balance updates
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param o The account
     * @param arg The balance
     */
    public void update(Observable o, Object arg) {
        // does stuff
    }

}
