package com.pec.personalexpensescontrol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    @Id
    private ObjectId id;
    @NotNull
    private String bankName;
    @NotNull
    private String bankAgency;
    @NotNull
    private String bankAccount;
    private BigDecimal accountBalance;
    private Date balanceUpdatedDate;

}
