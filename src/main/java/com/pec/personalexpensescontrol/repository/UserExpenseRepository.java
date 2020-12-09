package com.pec.personalexpensescontrol.repository;

import com.pec.personalexpensescontrol.model.UserExpense;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserExpenseRepository extends MongoRepository<UserExpense, ObjectId> {
    Optional<UserExpense> findByUserId(String userId);

    Optional<UserExpense> findByUserIdAndExpensesExpenseId(String userId, ObjectId expenseId);

    Optional<UserExpense> findByUserIdAndExpensesExpenseName(String userId, String expenseName);
}
