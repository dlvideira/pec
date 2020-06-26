package com.pec.personalexpensescontrol.model;

import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "userExpenses")
public class UserExpense extends AbstractMongoModel {
    @NotNull
    private String userName;
    @Nullable
    private List<Expense> expenses;
}
