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
public class Expense {
    private ObjectId expenseId = new ObjectId();
    private Date expenseCreatedDate;
    private Date expenseLastUpdatedDate;
    @NotNull(message = "expenseName must not be null")
    private String expenseName;
    @NotNull(message = "amount must not be null")
    private BigDecimal amount;
    private int currentParcel;
    private int totalParcels;
    private int frequency;
    @NotNull(message = "category must not be null")
    private Category category;
}