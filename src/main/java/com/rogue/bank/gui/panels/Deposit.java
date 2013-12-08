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
package com.rogue.bank.gui.panels;

import com.rogue.bank.Bank;
import com.rogue.bank.gui.AbsPanel;

/**
 * Handles depositing money
 * 
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class Deposit extends AbsPanel {
    
    /**
     * Deposit constructor
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param project The main {@link Bank}
     */
    public Deposit(Bank project){
        super(project);
    }

    @Override
    public String getTitle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int[] getPanelSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
