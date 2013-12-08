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

import com.rogue.bank.batch.BatchManager;
import com.rogue.bank.control.BankController;
import com.rogue.bank.data.DataManager;
import com.rogue.bank.gui.GUIManager;

/**
 * Main class for Bank
 *
 * @since 1.0.0
 * @author Spencer Alderman
 * @version 1.0.0
 */
public class Bank {

    private final DataManager data;
    private final GUIManager gui;
    private final BatchManager batch;
    private final BankController control;
    private final Session session;

    /**
     * Initial method
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            new Bank(args[0], args[1]);
        } else if (args.length == 1) {
            new Bank(args[0], null);
        } else {
            System.err.println("Usage: java Bank bankFile [batchFile]");
            System.exit(1);
        }
    }

    /**
     * Bank constructor
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param bankFile The main banking file location to access
     * @param batchFile A batch file location for quick execution
     */
    public Bank(String bankFile, String batchFile) {
        this.data = new DataManager(this, bankFile);
        this.control = new BankController(this);
        this.session = new Session();
        if (batchFile == null) {
            this.gui = new GUIManager(this);
            this.batch = null;
        } else {
            this.gui = null;
            this.batch = new BatchManager(this, batchFile);
            this.batch.execute();
        }
    }

    /**
     * Gets the {@link DataManager} for the project
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return The {@link DataManager}
     */
    public DataManager getDataManager() {
        return this.data;
    }

    /**
     * Gets the {@link GUIManager} for the project
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return The {@link GUIManager}
     */
    public GUIManager getGUIManager() {
        return this.gui;
    }
    
    /**
     * Gets the {@link BankController} for the project
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @return The {@link BankController}
     */
    public BankController getBankController() {
        return this.control;
    }
    
    /**
     * Returns the current {@link Session} for the project
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @return The current {@link Session}
     */
    public Session getSession() {
        return this.session;
    }

}
