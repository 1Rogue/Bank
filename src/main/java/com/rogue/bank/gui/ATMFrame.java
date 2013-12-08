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
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    
    private JPanel getTextField() {
        JPanel back = new JPanel();
        text.setEditable(false);
        text.setPreferredSize(new Dimension(200, 20));
        back.add(text);
        return back;
    }

    private JPanel getLabel() {
        JPanel back = new JPanel();
        back.setLayout(new BoxLayout(back, BoxLayout.X_AXIS));
        back.add(Box.createHorizontalGlue());
        back.add(this.label);
        back.add(Box.createHorizontalGlue());
        this.add(back, BorderLayout.NORTH);
        return back;
    }

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
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                current = current.execOK();
                //verify account x1
                //verify pin x1
                //verify deposit x1
                //verify withdrawal x1
                //back to transaction x4
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

    private ActionListener getNumberListner(final String i) {    
        return new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                updateText(text.getText() + i);
            }

        };
    }
    
    public void updateText(final String in) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                text.setText(in);
            }
            
        });
    }
    
    private void updateLabel(final String text) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                label.setText(text);
            }
            
        });
    }

}

enum ATMState {
        WELCOME("Welcome to ACME Banking") {
            @Override
            public ATMState execOK() {
                //verification
                return ATMState.ENTERPIN;
            }
        },
        ENTERPIN("Please enter your PIN number") {
            @Override
            public ATMState execOK() {
                //verification
                return ATMState.TRANSACTION;
            }
        },
        TRANSACTION("Press 1 to view balance, 2 to withdraw, and 3 to deposit") {
            @Override
            public ATMState execOK() {
                return this;
            }
        },
        DEPOSIT("Select the amount you would like to deposit") {
            @Override
            public ATMState execOK() {
                //verification
                return ATMState.SUCCESS;
            }
        },
        WITHDRAW("Select the amount you would like to withdraw") {
            @Override
            public ATMState execOK() {
                //verification
                //success or failure
                return ATMState.SUCCESS;
            }
        },
        BALANCE("BEHOLD: YOUR BALANCE!!1!") {
            @Override
            public ATMState execOK() {
                //get and display balance
                return ATMState.TRANSACTION;
            }
        },
        SUCCESS("Transaction successful") {
            @Override
            public ATMState execOK() {
                return ATMState.TRANSACTION;
            }
        },
        FAILURE("Transaction failed") {
            @Override
            public ATMState execOK() {
                return ATMState.TRANSACTION;
            }
        };
        
        private final String def;
        
        private ATMState(String def) {
            this.def = def;
        }
        
        public String getLabel() {
            return this.def;
        }
        
        public abstract ATMState execOK();
    }