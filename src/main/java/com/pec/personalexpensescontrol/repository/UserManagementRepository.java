package com.pec.personalexpensescontrol.repository;

import com.pec.personalexpensescontrol.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserManagementRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByEmail(String email);
}
