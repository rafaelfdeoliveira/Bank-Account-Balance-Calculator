package Models;


import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

@Getter
@Setter
@RequiredArgsConstructor
public class BankAccount {
    @NonNull private String id;
    @NonNull private BankName bankName;
    @NonNull private String branchId;
    @NonNull private String accountNumber;
    private float balance = 0;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BankAccount)) return false;
        BankAccount otherAccount = (BankAccount) o;
        return this.id.equals(otherAccount.getId());
    }

    public void addToBalance(float amount) {
        this.balance += amount;
    }
}
