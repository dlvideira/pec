package com.pec.personalexpensescontrol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "userManagement")
public class User extends AbstractMongoModel {
    @NotNull
    private String userName;
    @NotNull
    private String password;
    @NotNull
    @Email
    private String email;
}
