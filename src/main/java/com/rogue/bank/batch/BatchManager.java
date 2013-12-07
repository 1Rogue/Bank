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
package com.rogue.bank.batch;

import com.rogue.bank.Bank;
import com.rogue.bank.batch.commands.ApplyInterestCommand;
import com.rogue.bank.batch.commands.CloseCommand;
import com.rogue.bank.batch.commands.DepositCommand;
import com.rogue.bank.batch.commands.OpenCommand;
import com.rogue.bank.batch.commands.WithdrawCommand;
import com.rogue.bank.data.Account;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Managing class for batch mode execution
 *
 * @since 1.0.0
 * @author Robert Carmosino
 * @version 1.0.0
 */
public class BatchManager {

    private final Bank project;
    private final File batchFile;
    private final Map<Character, BatchCommand> commands;

    /**
     * Construct a new BatchManager with given file name.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param project The Bank to execute onto.
     * @param fileName The name of the batch file.
     */
    public BatchManager(Bank project, String fileName) {
        this.project = project;
        this.batchFile = new File(fileName);
        this.commands = new HashMap<Character, BatchCommand>();
        this.registerCommands();
    }

    /**
     * Registers the map of commands used in execution.
     *
     * @since 1.0.0
     * @version 1.0.0
     */
    private void registerCommands() {
        BatchCommand[] cmds = {
            new OpenCommand(),
            new CloseCommand(),
            new DepositCommand(),
            new WithdrawCommand(),
            new ApplyInterestCommand()
        };

        for (BatchCommand cmd : cmds) {
            this.commands.put(cmd.getChar(), cmd);
        }
    }

    /**
     * Executes the batch file, running all commands.
     *
     * @since 1.0.0
     * @version 1.0.0
     */
    public void execute() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(this.batchFile));
        } catch (FileNotFoundException ex) {
            System.err.println("File does not exist.");
            return;
        }

        this.displayInitialBankData();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                BatchCommand command = this.commands.get(line.charAt(0));
                if (command != null) {
                    String[] args;
                    if (line.length() < 2) {
                        args = new String[0];
                    } else {
                        args = line.substring(2).split(" ");
                    }
                    command.execute(this.project.getBankController(), args);
                }
            }
            this.displayFinalBankData();
        } catch (IOException e) {
            System.err.println("Error reading the batch file.");
        }
    }

    /**
     * Displays initial bank data.
     *
     * @since 1.0.0
     * @version 1.0.0
     */
    private void displayInitialBankData() {
        System.out.println("========== Initial Bank Data ==================");
        System.out.println();
        System.out.println("Account Type    Account Balance");
        System.out.println("------------    ------- -----------");

        List<Account> accounts = new ArrayList<Account>(this.project.getBankController().getAccounts());
        Collections.sort(accounts);
        for (Account account : accounts) {
            System.out.format("-11%s $    %7d $ %9.2f", account.getDisplayString(), account.getID(), account.getBalance());
        }

        System.out.println();
        System.out.println("===============================================");
        System.out.println();
    }

    /**
     * Displays final bank data.
     *
     * @since 1.0.0
     * @version 1.0.0
     */
    private void displayFinalBankData() {
        System.out.println("==========   Final Bank Data ==================");
        System.out.println();
        System.out.println("Account Type    Account Balance");
        System.out.println("------------    ------- -----------");

        List<Account> accounts = new ArrayList<Account>(this.project.getBankController().getAccounts());
        Collections.sort(accounts);
        for (Account account : accounts) {
            System.out.format("-11%s $    %7d $ %9.2f", account.getDisplayString(), account.getID(), account.getBalance());
        }

        System.out.println();
        System.out.println("===============================================");
        System.out.println();
    }
}
