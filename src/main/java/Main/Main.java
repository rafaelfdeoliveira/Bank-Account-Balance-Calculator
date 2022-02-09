package Main;

import BalanceCalculator.BalanceCalculator;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path inputPath = Path.of("src","main","resources", "operacoes.csv");
        BalanceCalculator balanceCalculator = new BalanceCalculator(inputPath);

        // Save the Bank Statement File for each Bank Account in the target/output directory. The operations are presented in chronological order. The name of the file is the bankAccountId.
        Path outputDirPath = Path.of("target", "output");
        balanceCalculator.saveBankStatements(outputDirPath);
    }

}
