package com.pec.personalexpensescontrol.repository;

import com.pec.personalexpensescontrol.model.BankAccountData;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BankAccountRepository extends MongoRepository<BankAccountData, ObjectId> {

}
