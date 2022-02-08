package Main;

import java.io.IOException;
import java.io.FileReader;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import Models.BankAccount;
import Models.BankingOperation;
import com.opencsv.CSVReader;

import Models.BankName;
import Models.OperationType;


public class Main {
    public static void main(String[] args) {
        HashMap<String, TreeSet<BankingOperation>> operationsMap = getAllData();


//        File diretorio = new File("extratos");
//
//        if (diretorio.exists()) {
//            diretorio.delete();
//            diretorio.mkdir();
//        } else {
//            diretorio.mkdir();
//        }
//
//        File operacoesFile = new File("extrato-antigo.txt");
//        if (operacoesFile.exists()) {
//            operacoesFile.delete();
//        }
    }

    public static HashMap<String, TreeSet<BankingOperation>> getAllData() {
        Path path = Path.of("src","main","resources", "operacoes.csv");
        CSVReader reader;
        // HashMap where keys are unique bankAccountIds and each value is a TreeSet of the bankingOperations
        HashMap<String, TreeSet<BankingOperation>> operationsMap = new HashMap<>();
        try {
            reader = new CSVReader(new FileReader(String.valueOf(path)));
            // Ignore the data headings (first line in the csv file)
            reader.readNext();

            String[] line;
            while ((line = reader.readNext()) != null) {
                String operator = line[5];
                OperationType type = OperationType.valueOf(line[6].toUpperCase());
                float ammount = Float.parseFloat(line[7]);
                LocalDateTime operationDate = LocalDateTime.parse(line[0], DateTimeFormatter.ISO_DATE_TIME);
                String bankAccountId = line[1];
                BankName bankName = BankName.valueOf(line[2].toUpperCase());
                String branchId = line[3];
                String accountNumber = line[4];
                BankAccount bankAccount = new BankAccount(bankAccountId, bankName, branchId, accountNumber);
                BankingOperation bankingOperation = new BankingOperation(operator, type, ammount, operationDate, bankAccount);

                if (!operationsMap.containsKey(bankAccountId)) operationsMap.put(bankAccountId, new TreeSet<>());
                operationsMap.get(bankAccountId).add(bankingOperation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return operationsMap;
    }

}

//Banco Santander
//Agencia: ...
//Conta: ...
//
//Data   Tipo   Valor   Operador
//04-01-22 11:24:42  DEPOSITO  +100   Felipe
//
//Saldo: ... (saldo final na conta)

