package com.pec.personalexpensescontrol.repository;

import com.pec.personalexpensescontrol.infra.security.QUser;
import com.pec.personalexpensescontrol.model.UserExpense;
import com.querydsl.core.types.dsl.StringPath;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Optional;


public interface UserExpenseRepository extends MongoRepository<UserExpense, ObjectId>,
        QuerydslPredicateExecutor<UserExpense>,
        QuerydslBinderCustomizer<QUser> {
    Optional<UserExpense> findById(String userId);
    Optional<UserExpense> findByIdAndExpensesExpenseName(String userId, String expenseName);

    default void customize(QuerydslBindings bindings, QUser user) {
        bindings.bind(String.class)
                .first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
