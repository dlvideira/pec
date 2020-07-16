package com.pec.personalexpensescontrol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    @NotNull
    private ObjectId bankAccountId = new ObjectId();
    @NotNull
    private Date bankAccountCreatedDate;
    @NotNull
    private Date bankAccountLastUpdatedDate;
    @NotNull
    private String bankName;
    @NotNull
    private String bankAgency;
    @NotNull
    private String bankAccountNumber;
    private BigDecimal accountBalance;

}
