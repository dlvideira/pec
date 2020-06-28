package com.pec.personalexpensescontrol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private ObjectId expenseId = new ObjectId();
    @NotNull(message = "expenseName must not be null")
    private String expenseName;
    private Date expenseCreatedDate;
    private Date expenseLastUpdatedDate;
    @NotNull(message = "amount must not be null")
    private long amount;
    private int currentParcel;
    private int totalParcels;
    private int frequency;
    @NotNull(message = "category must not be null")
    private Category category;
}
///TODO tentar usar o AbstractMongoModel como o User.java (n'ao funciona em classe que não é Document??)