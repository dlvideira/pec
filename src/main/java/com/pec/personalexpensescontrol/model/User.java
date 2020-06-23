package com.pec.personalexpensescontrol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "userExpenses")
public class User extends AbstractMongoModel {
    private String userName;
    private List<Expense> expenses;
}
