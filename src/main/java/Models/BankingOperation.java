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
    private float ammount;
    private LocalDateTime date;
    private BankAccount account;

    @Override
    public boolean equals(Object o) {

        return false;
    }

    @Override
    public int compareTo(BankingOperation o) {
        return this.getDate().compareTo(o.getDate());
    }
}
