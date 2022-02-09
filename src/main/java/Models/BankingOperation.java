package Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BankingOperation implements Comparable<BankingOperation> {
    private String operator;
    private OperationType type;
    private float amount;
    private LocalDateTime date;
    private String bankAccountId;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BankingOperation)) return false;
        BankingOperation otherOperation = (BankingOperation) o;
        return  this.operator.equalsIgnoreCase(otherOperation.getOperator()) &&
                this.type.equals(otherOperation.getType()) &&
                Math.abs(this.amount - otherOperation.getAmount()) < 0.1 &&
                this.date.equals(otherOperation.getDate()) &&
                this.bankAccountId.equals(otherOperation.getBankAccountId());
    }

    @Override
    public int compareTo(BankingOperation operation) {
        return this.date.compareTo(operation.getDate());
    }
}
