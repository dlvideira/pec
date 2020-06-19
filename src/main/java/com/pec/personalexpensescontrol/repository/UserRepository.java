package com.pec.personalexpensescontrol.repository;

import com.pec.personalexpensescontrol.model.QUser;
import com.pec.personalexpensescontrol.model.User;
import com.querydsl.core.types.dsl.StringPath;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;


public interface UserRepository extends MongoRepository<User, ObjectId>,
        QuerydslPredicateExecutor<User>,
        QuerydslBinderCustomizer<QUser> {
    User findById(String userId);

    default void customize(QuerydslBindings bindings, QUser user) {
        bindings.bind(String.class)
                .first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
