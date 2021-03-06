package com.pec.personalexpensescontrol.infra.security;

import com.pec.personalexpensescontrol.model.AbstractMongoModel;
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
    private String username;
    @NotNull
    private String password;
    @NotNull
    @Email(message = "Não é um endereço de e-mail válido.")
    private String email;
    private Role role;
    private boolean isActive;

    public static User create(String username, String password, String email, Role role, boolean isActive) {
        return new User(username, password, email, role, isActive);
    }
}
