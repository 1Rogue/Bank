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
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Login panel for {@link GUIWindow}
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class Welcome extends AbsPanel {
    
    private JLabel label = new JLabel("");
    
    /**
     * Welcome constructor
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param project The main {@link Bank}
     */
    public Welcome(final Bank project){
        super(project);
        
        this.setLayout(new BorderLayout());
        this.add(this.label, BorderLayout.NORTH);
        JPanel main = new JPanel();
        main.setLayout(new FlowLayout(FlowLayout.RIGHT));
        main.add(new JLabel("Enter Account ID:"));
        final JTextField acc = new JTextField();
        acc.setPreferredSize(new Dimension(150,30));
        main.add(acc);
        JButton jb = new JButton("Submit");
        jb.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String val = acc.getText();
                try {
                    int id = Integer.parseInt(val);
                    if (!project.getBankController().validAccount(id)) {
                        updateLabel("Please enter a valid account ID");
                    } else {
                        project.getSession().setAccount(project.getBankController().getAccount(id));
                        project.getGUIManager().getWindow().swapWindow(new EnterPIN(project));
                    }
                } catch (NumberFormatException ex) {
                    updateLabel("Please enter a number");
                }
            }
            
        });
        main.add(jb);
        this.add(main, BorderLayout.SOUTH);
    }
    
    /**
     * Sets the text of an error label
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param update The string to set
     */
    private void updateLabel(final String update) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                label.setText(update);
            }
            
        });
    }

    @Override
    public String getTitle() {
        return "Welcome to ACME Banking";
    }

    @Override
    public int[] getPanelSize() {
        return new int[] {350, 100};
    }

}
