package com.pec.personalexpensescontrol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private String expenseName;
    @NotNull(message = "expenseCreatedDate must not be null")
    private DateTime expenseCreatedDate;
    @NotNull(message = "expenseLastUpdatedDate must not be null")
    private DateTime expenseLastUpdatedDate;
    private long amount;
    private int currentParcel;
    private int totalParcels;
    private int frequency;
    private Category category;
}
///TODO tentar usar o AbstractMongoModel como o User.java (n'ao funciona em classe que não é Document??)