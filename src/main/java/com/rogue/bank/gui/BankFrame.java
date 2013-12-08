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
import com.rogue.bank.data.Account;
import com.rogue.bank.data.UpdatePacket;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * BankFrame for showcasing account information.
 *
 * @since 1.0.0
 * @author 1Rogue
 * @author CrypticStorm
 * @version 1.0.0
 */
public class BankFrame extends JFrame {

    private final Bank project;
    private final JLabel infoLabel;
    private final JList list;
    private final JPanel buttonPanel;
    private final ArrayList<String> accStrings;

    /**
     * BankFrame constructor
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param project Main {@link Bank} instance
     */
    public BankFrame(Bank project) {
        this.project = project;
        this.accStrings = this.loadAccounts();
        this.setTitle("Robert Carmosino && Spencer Alderman");
        this.setLayout(new BorderLayout());

        this.add(this.infoLabel = this.createLabel(), BorderLayout.NORTH);
        this.add(this.list = this.createList(), BorderLayout.CENTER);
        this.add(this.buttonPanel = this.createPanel(), BorderLayout.SOUTH);

        this.setSize(400, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Retrieves a list of formatted accounts for a JList
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return A string of formatted accounts
     */
    private ArrayList<String> loadAccounts() {
        Collection<Account> accounts = this.project.getBankController().getAccounts();
        ArrayList<String> strings = new ArrayList<String>();
        int i = 0;
        for (Account acc : accounts) {
            strings.add(String.format("%-7s%-19s%-9.2f", acc.getID(), acc.getDisplayString(), acc.getBalance()));
            i++;
        }
        return strings;
    }

    /**
     * Refreshes the account list
     *
     * @since 1.0.0
     * @version 1.0.0
     */
    private void refreshAccounts() {
        for (int i = 0; i < this.accStrings.size(); i++) {
            String str = this.accStrings.get(i);
            int id = Integer.valueOf(str.substring(0, str.indexOf(' ')));
            Account acc = this.project.getBankController().getAccount(id);
            this.accStrings.set(i, String.format("%-7s%-19s%-9.2f", acc.getID(), acc.getDisplayString(), acc.getBalance()));
        }
        this.list.setListData(this.accStrings.toArray(new String[this.accStrings.size()]));
        this.refreshLabel();
    }

    /**
     * Refreshes the BankFrame label
     *
     * @since 1.0.0
     * @version 1.0.0
     */
    private void refreshLabel() {
        this.infoLabel.setText("" + new SimpleDateFormat("H:mm").format(new Date()));
    }

    /**
     * Creates and returns the JLabel information
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return A {@link JLabel} for the BankFrame
     */
    private JLabel createLabel() {
        JLabel label = new JLabel("Bank Accounts");
        return label;
    }

    /**
     * Creates the account information list
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return A {@link JList} of account information
     */
    private JList createList() {
        JList jList = new JList();
        jList.setListData(this.accStrings.toArray(new String[this.accStrings.size()]));
        return jList;

    }

    /**
     * Creates the panel for buttons
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return The panel for buttons
     */
    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JButton atmButton = new JButton("Open ATM");
        atmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                project.getGUIManager().deployATM();
            }
        });
        panel.add(atmButton, BorderLayout.WEST);

        JButton updateButton = new JButton("Update Accounts");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshAccounts();
            }
        });
        panel.add(updateButton, BorderLayout.EAST);

        return panel;
    }

    /**
     * Updates an account with specific UpdatePacket.
     *
     * @param acc THe account to update
     * @param packet The type of update
     */
    public void updateAccount(Account acc, UpdatePacket packet) {
        packet.update(this.accStrings, acc);
        this.list.setListData(this.accStrings.toArray(new String[this.accStrings.size()]));
        this.refreshLabel();
    }
}
