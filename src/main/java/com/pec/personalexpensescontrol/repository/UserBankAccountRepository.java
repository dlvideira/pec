package com.pec.personalexpensescontrol.repository;

import com.pec.personalexpensescontrol.model.UserBankAccount;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserBankAccountRepository extends MongoRepository<UserBankAccount, ObjectId> {

    Optional<UserBankAccount> findByUserIdAndBankAccountsBankAccountNumber(String userId, String bankAccountNumber);

    Optional<UserBankAccount> findByUserIdAndBankAccountsBankAccountId(String userId, ObjectId bankAccountId);

    Optional<UserBankAccount> findByUserId(String userId);

}