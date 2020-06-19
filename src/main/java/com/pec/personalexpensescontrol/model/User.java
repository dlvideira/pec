package com.pec.personalexpensescontrol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "userExpenses")
public class User {
    @Id
    private ObjectId id;
    private String userName;
    private Expense expense;
    @NotNull(message = "createdDate must not be null")
    private Date createdDate;
    @NotNull(message = "lastUpdatedDate must not be null")
    private Date lastUpdatedDate;
}
