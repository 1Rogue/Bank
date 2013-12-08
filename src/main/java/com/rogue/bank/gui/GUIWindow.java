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
import com.rogue.bank.gui.panels.*;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Main window instance
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public final class GUIWindow extends JFrame {

    private final Bank project;

    /**
     * {@link GUIWindow} constructor
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param project Main {@link Bank} instance
     */
    public GUIWindow(Bank project) {
        this.project = project;

        this.swapWindow(new Welcome(this.project));
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Swaps the current GUI panel with a new one
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param panel The panel to swap with
     */
    public void swapWindow(final AbsPanel panel) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                getContentPane().removeAll();
                setLayout(new BorderLayout());
                add(panel, BorderLayout.CENTER);
                setTitle(panel.getTitle());
                int[] size = panel.getPanelSize();
                panel.setSize(size[0], size[1]);
                setSize(size[0], size[1]);
                setLocationRelativeTo(null);
                setVisible(false);
                validate();
                setVisible(true);
                repaint();
            }

        });
    }

}
