package Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BankAccount {
    private String id;
    private BankName bankName;
    private String branchId;
    private String accountNumber;
}
