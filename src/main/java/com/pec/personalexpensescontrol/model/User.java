package com.pec.personalexpensescontrol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "userExpenses")
public class User {
    @Id
    private ObjectId id;
    private String userName;
    private List<Expense> expenses;
    @NotNull(message = "createdDate must not be null")
    private Date createdDate;
    @NotNull(message = "lastUpdatedDate must not be null")
    private Date lastUpdatedDate;

    public void addExpense(Expense expense){
        if(expenses == null){
            expenses = new ArrayList<>();

            expenses.add(expense);
        }
    }
}
