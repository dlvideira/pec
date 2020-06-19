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
    private String expenseName;
    @NotNull(message = "expenseCreatedDate must not be null")
    private Date expenseCreatedDate;
    @NotNull(message = "expenseLastUpdatedDate must not be null")
    private Date expenseLastUpdatedDate;
    private long amount;
    private int currentParcel;
    private int totalParcels;
    private int frequency;
    private Category category;
}
