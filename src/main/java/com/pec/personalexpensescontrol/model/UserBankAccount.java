package com.pec.personalexpensescontrol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "userBankAccount")
public class UserBankAccount extends AbstractMongoModel{
    @NotNull
    private String userId;
    private List<BankAccount> bankAccounts;
}
