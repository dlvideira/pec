package com.pec.personalexpensescontrol.repository;

import com.pec.personalexpensescontrol.model.BankAccount;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BankAccountRepository extends MongoRepository<BankAccount, ObjectId> {
    Optional<BankAccount> findByBankAccount (String bankAccount);

}
