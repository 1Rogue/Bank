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

import com.rogue.bank.control.Session;
import com.rogue.bank.Bank;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Basic ATM Frame
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class ATMFrame extends JFrame {

    private final Bank project;
    private final JLabel label = new JLabel("");
    private final JTextField text = new JTextField();
    private final Session sess = new Session();
    private ATMState current = ATMState.WELCOME;
    private int accid = -1;
    
    /**
     * Enum representing state within the ATM GUI. Holds abstract method for the
     * OK button usage.
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    enum ATMState {
        WELCOME("Welcome to ACME Banking") {
            @Override
            public ATMState execOK(ATMFrame frame) {
                String val = frame.text.getText();
                try {
                    int id = Integer.parseInt(val);
                    if (frame.project.getBankController().validAccount(id)) {
                        frame.accid = id;
                        return ATMState.ENTERPIN;
                    } else {
                        frame.updateLabel("Please enter a valid account ID");
                    }
                } catch (NumberFormatException ex) {
                    frame.updateLabel("Please enter a number");
                }
                return this;
            }
        },
        ENTERPIN("Please enter your PIN number") {
            @Override
            public ATMState execOK(ATMFrame frame) {
                String val = frame.text.getText();
                try {
                    int pin = Integer.parseInt(val);
                    if (frame.project.getBankController().validLogin(frame.sess, frame.accid, pin)) {
                        return ATMState.TRANSACTION;
                    }
                } catch (NumberFormatException ex) {
                    frame.updateLabel("Please enter a number");
                }
                return this;
            }
        },
        TRANSACTION("Enter 1 to view balance, 2 to withdraw, and 3 to deposit") {
            @Override
            public ATMState execOK(ATMFrame frame) {
                String val = frame.text.getText();
                try {
                    int act = Integer.parseInt(val);
                    switch(act) {
                        case 1:
                            return ATMState.BALANCE;
                        case 2:
                            return ATMState.WITHDRAW;
                        case 3:
                            return ATMState.DEPOSIT;
                    }
                } catch (NumberFormatException ex) {
                    frame.updateLabel("Please enter a number");
                }
                return this;
            }
        },
        DEPOSIT("Select the amount you would like to deposit") {
            @Override
            public ATMState execOK(ATMFrame frame) {
                String val = frame.text.getText();
                try {
                    double amount = Double.parseDouble(val);
                    if (frame.project.getBankController().deposit(frame.sess, amount)) {
                        return ATMState.SUCCESS;
                    }
                } catch (NumberFormatException ex) {
                    frame.updateLabel("Please enter a number");
                }
                return ATMState.DEPOSIT;
            }
        },
        WITHDRAW("Select the amount you would like to withdraw") {
            @Override
            public ATMState execOK(ATMFrame frame) {
                String val = frame.text.getText();
                try {
                    double amount = Double.parseDouble(val);
                    if (frame.project.getBankController().withdraw(frame.sess, amount)) {
                        return ATMState.SUCCESS;
                    } else {
                        return ATMState.FAILURE;
                    }
                } catch (NumberFormatException ex) {
                    frame.updateLabel("Please enter a number");
                }
                return ATMState.WITHDRAW;
            }
        },
        BALANCE("Your current balance is {balance}. Press \"OK\" to return.") {
            @Override
            public ATMState execOK(ATMFrame frame) {
                return ATMState.TRANSACTION;
            }
        },
        SUCCESS("Transaction successful") {
            @Override
            public ATMState execOK(ATMFrame frame) {
                return ATMState.TRANSACTION;
            }
        },
        FAILURE("Transaction failed") {
            @Override
            public ATMState execOK(ATMFrame frame) {
                return ATMState.TRANSACTION;
            }
        };
        
        private final String def;
        
        /**
         * Private constructor for ATMState
         * 
         * @since 1.0.0
         * @version 1.0.0
         * 
         * @param def The default label for this state
         */
        private ATMState(String def) {
            this.def = def;
        }
        
        /**
         * Gets the default label for this state
         * 
         * @since 1.0.0
         * 2version 1.0.0
         * 
         * @return The default label
         */
        public String getLabel() {
            return this.def;
        }
        
        /**
         * Executor method for the OK button
         * 
         * @since 1.0.0
         * @version 1.0.0
         * 
         * @param frame The {@link ATMFrame} instance this applies to
         * @return The new ATMState
         */
        public abstract ATMState execOK(ATMFrame frame);
    }

    /**
     * ATMFrame constructor
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param project The main {@link Bank} instance
     * @param id The unique ATM id
     */
    public ATMFrame(Bank project, int id) {
        this.project = project;
        this.setLayout(new BorderLayout());
        JPanel textStuff = new JPanel();
        textStuff.setLayout(new BorderLayout());
        textStuff.add(this.getTextField(), BorderLayout.NORTH);
        textStuff.add(this.getLabel(), BorderLayout.SOUTH);
        this.add(textStuff, BorderLayout.NORTH);
        this.add(this.getOperationButtons(), BorderLayout.CENTER);
        this.setSize(400, 300);
        this.label.setText(this.current.getLabel());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("ATM " + id + ". Robert Carmosino && Spencer Alderman");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    
    /**
     * Initializes the text panel
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @return The new {@link JPanel} with the text box
     */
    private JPanel getTextField() {
        JPanel back = new JPanel();
        text.setEditable(false);
        text.setPreferredSize(new Dimension(200, 20));
        back.add(text);
        return back;
    }

    /**
     * Initializes the label panel
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @return The new {@link JPanel} with the label
     */
    private JPanel getLabel() {
        JPanel back = new JPanel();
        back.setLayout(new BoxLayout(back, BoxLayout.X_AXIS));
        back.add(Box.createHorizontalGlue());
        back.add(this.label);
        back.add(Box.createHorizontalGlue());
        this.add(back, BorderLayout.NORTH);
        return back;
    }

    /**
     * Initializes the button panel
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @return The new {@link JPanel} of buttons
     */
    private JPanel getOperationButtons() {
        JPanel back = new JPanel();
        back.setLayout(new GridLayout(5, 3));
        for (int i = 1; i < 10; i++) {
            JButton btrn = new JButton("" + i);
            btrn.addActionListener(this.getNumberListner(i + ""));
            back.add(btrn);
        }
        JButton zero = new JButton("0");
        zero.addActionListener(this.getNumberListner("0"));
        back.add(zero);
        JButton dec = new JButton(".");
        dec.addActionListener(this.getNumberListner("."));
        back.add(dec);
        JButton clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                updateText("");
            }

        });
        back.add(clear);
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (sess.getAccount() == null) {
                    current = ATMState.WELCOME;
                } else {
                    current = ATMState.TRANSACTION;
                }
                updateLabel(current.getLabel());
            }

        });
        back.add(cancel);
        JButton ok = new JButton("OK");
        final ATMFrame frame = this;
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ATMState state = current.execOK(frame);
                if (state != current) {
                    current = state;
                    updateLabel(current.getLabel());
                }
                updateText("");
            }

        });
        back.add(ok);
        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }

        });
        back.add(close);
        return back;
    }

    /**
     * Gets the listener for standard number buttons that append the {@link JTextField}
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param i The string the button represents
     * @return A new {@link ActionListener}
     */
    private ActionListener getNumberListner(final String i) {    
        return new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                updateText(text.getText() + i);
            }

        };
    }
    
    /**
     * Updates the numerical text box
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param in The string to update to
     */
    public void updateText(final String in) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                text.setText(in);
            }
            
        });
    }
    
    /**
     * Updates the readable label
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param in The string to update to
     */
    private void updateLabel(final String in) {
        final ATMFrame frame = this;
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                String txt = in;
                if(frame.sess.getAccount() != null) {
                    System.out.println("replacing");
                    txt = txt.replace("{balance}", String.format("$%.2f", frame.project.getBankController().getBalance(frame.sess)));
                }
                label.setText(txt);
            }
            
        });
    }

}