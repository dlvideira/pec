package com.pec.personalexpensescontrol.repository;

import com.pec.personalexpensescontrol.model.BankAccount;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BankAccountRepository extends MongoRepository<BankAccount, ObjectId> {
    Optional<BankAccount> findByBankAccountNumber (String bankAccountNumber);
    Optional<BankAccount> findByBankAccountNumberAndBankName (String bankAccountNumber, String bankName);
    Optional<BankAccount> findById (String id);

}
