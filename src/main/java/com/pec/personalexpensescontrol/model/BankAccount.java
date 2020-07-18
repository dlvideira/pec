package com.pec.personalexpensescontrol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    private ObjectId bankAccountId = new ObjectId();
    private Date bankAccountCreatedDate;
    private Date bankAccountLastUpdatedDate;
    @NotNull(message = "bankName must not be null")
    private String bankName;
    @NotNull(message = "bankAgency must not be null")
    private String bankAgency;
    @NotNull(message = "bankAccountNumber must not be null")
    private String bankAccountNumber;
    private BigDecimal accountBalance;
}
