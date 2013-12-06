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
import javax.swing.JPanel;

/**
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public abstract class AbsPanel extends JPanel {
    
    private final Bank project;
    
    /**
     * Abstract panel constructor
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param project Main {@link Bank} instance
     */
    public AbsPanel(Bank project) {
        
        this.project = project;
    }
    
    /**
     * Disposes the current JPanel instance, and handles cleanup
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public void dispose() {
        this.dispose();
    }
    
    /**
     * Returns the appropriate panel
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @return The {@link AbsPanel} instance
     */
    public AbsPanel getPanel() {
        return this;
    }
    
    /**
     * Gets the current title to use for the GUI Frame
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @return The GUI Frame's title
     */
    public abstract String getTitle();
    
}
