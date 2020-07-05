package com.pec.personalexpensescontrol.repository;

import com.pec.personalexpensescontrol.infra.security.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserManagementRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(String userId);
}
