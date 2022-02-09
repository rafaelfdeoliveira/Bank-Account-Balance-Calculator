package BalanceCalculator;

import Models.BankAccount;
import Models.BankName;
import Models.BankingOperation;
import Models.OperationType;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class BalanceCalculator {
    // Keys are bankAccountIds
    private final HashMap<String, TreeSet<BankingOperation>> operationsMap = new HashMap<>();
    private final HashMap<String, BankAccount> accountsMap = new HashMap<>();

    public BalanceCalculator(Path path) {
        this.importAllData(path);
        this.executeOperations();
    }

    public void saveBankStatements(Path outputDirPath) {
        this.createDirIfNotExists(outputDirPath);
        this.operationsMap.forEach((accountId, operations) -> {
            Path outputPath = Path.of(outputDirPath.toString(), accountId + ".txt");
            BankAccount bankAccount = this.accountsMap.get(accountId);
            ArrayList<String> bankStatementInfo = new ArrayList<>();
            bankStatementInfo.add("Banco " + bankAccount.getBankName());
            bankStatementInfo.add("Agencia: " + bankAccount.getBranchId());
            bankStatementInfo.add("Conta: " + bankAccount.getAccountNumber());
            bankStatementInfo.add("");
            bankStatementInfo.add("       Data           Operador" + " ".repeat(7) + "Tipo" + " ".repeat(7) + "Valor");

            for (BankingOperation op : operations) {
                String operationInfo = op.getDate().toString() + " ".repeat(3) + op.getOperator() + " ".repeat(15 - op.getOperator().length()) + op.getType().toString() + " ".repeat(11 - op.getType().toString().length()) + op.getAmount();
                bankStatementInfo.add(operationInfo);
            }

            bankStatementInfo.add("");
            bankStatementInfo.add("Saldo: " + bankAccount.getBalance());
            try {
                Files.write(outputPath, bankStatementInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void importAllData(Path path) {
        try {
            CSVReader reader = new CSVReader(new FileReader(String.valueOf(path)));
            // Ignore the data headings (first line in the csv file)
            reader.readNext();

            String[] line;
            while ((line = reader.readNext()) != null) {
                String operator = line[5];
                OperationType type = OperationType.valueOf(line[6].toUpperCase());
                float ammount = Float.parseFloat(line[7]);
                LocalDateTime operationDate = LocalDateTime.parse(line[0], DateTimeFormatter.ISO_DATE_TIME);
                String bankAccountId = line[1];
                BankingOperation bankingOperation = new BankingOperation(operator, type, ammount, operationDate, bankAccountId);
                if (!this.operationsMap.containsKey(bankAccountId)) this.operationsMap.put(bankAccountId, new TreeSet<>());
                this.operationsMap.get(bankAccountId).add(bankingOperation);

                if (!this.accountsMap.containsKey(bankAccountId)) {
                    BankName bankName = BankName.valueOf(line[2].toUpperCase());
                    String branchId = line[3];
                    String accountNumber = line[4];
                    BankAccount bankAccount = new BankAccount(bankAccountId, bankName, branchId, accountNumber);
                    this.accountsMap.put(bankAccountId, bankAccount);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void executeOperations() {
        this.operationsMap.forEach((accountId, operations) -> {
            for (BankingOperation op : operations) {
                if (op.getType().equals(OperationType.DEPOSITO)) {
                    this.accountsMap.get(accountId).addToBalance(op.getAmount());
                } else if (op.getType().equals(OperationType.SAQUE)) {
                    this.accountsMap.get(accountId).addToBalance(- op.getAmount());
                }
            }
        });
    }

    private void createDirIfNotExists(Path dirPath) {
        try {
            if (Files.notExists(dirPath)) Files.createDirectory(dirPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}