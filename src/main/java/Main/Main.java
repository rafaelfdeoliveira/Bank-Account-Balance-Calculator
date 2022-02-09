package Main;

import BalanceCalculator.BalanceCalculator;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path inputPath = Path.of("src","main","resources", "operacoes.csv");
        BalanceCalculator balanceCalculator = new BalanceCalculator(inputPath);
        balanceCalculator.saveBankStatements();


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

}
