package com.pec.personalexpensescontrol.infra.security;

import com.pec.personalexpensescontrol.model.AbstractMongoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
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
    private Role role;
    private boolean isActive;

    public static User create(String username, String password, String email, Role role, boolean isActive){
        return new User(username, password, email, role, true);
        //TODO por enquanto HC active true, depois confirma;'ao por email pra ativar
    }
}
