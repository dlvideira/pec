package com.pec.personalexpensescontrol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    @NotNull
    private String expenseName;
    @NotNull(message = "expenseCreatedDate must not be null")
    private Date expenseCreatedDate;
    @NotNull(message = "expenseLastUpdatedDate must not be null")
    private Date expenseLastUpdatedDate;
    @NotNull
    private long amount;
    private int currentParcel;
    private int totalParcels;
    private int frequency;
    @NotNull
    private Category category;
}
///TODO tentar usar o AbstractMongoModel como o User.java (n'ao funciona em classe que não é Document??)