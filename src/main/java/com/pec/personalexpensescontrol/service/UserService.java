package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.infra.security.User;
import com.pec.personalexpensescontrol.repository.UserManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.pec.personalexpensescontrol.infra.security.User.create;

@Service
public class UserService {
    @Autowired
    UserManagementRepository userManagementRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createAccount(User newUserRequest) throws Exception {
        if (emailExist(newUserRequest.getEmail()))
            throw new Exception("Já existe uma conta associada com esse email.\nVocê esqueceu seu usuário?");

        var user = create(
                newUserRequest.getUserName(),
                passwordEncoder.encode(newUserRequest.getPassword()),
                newUserRequest.getEmail(),
                newUserRequest.getRole(),
                true);

        userManagementRepository.save(user);
    }

    public Optional updateEmail(String userId, String newUserEmail) throws Exception {
        if (emailExist(newUserEmail))
            throw new Exception("Já existe uma conta associada com esse email.");

        var user = userManagementRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setEmail(newUserEmail);
            userManagementRepository.save(user.get());
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public Optional updateUsername(String userId, String newUsername) {
        var user = userManagementRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setUserName(newUsername);
            userManagementRepository.save(user.get());
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public Optional updateUserActiveStatus(String userId) {
        var user = userManagementRepository.findById(userId);
        if (user.isPresent()) {
           // user.get().isActive() ? user.get().setActive(false) : user.get().setActive(true);
            //TODO porque esse ternario nao funciona?
            if (user.get().isActive()) {
                user.get().setActive(false);
            } else {
                user.get().setActive(true);
            }
            userManagementRepository.save(user.get());
            return Optional.of(user);
        }
        return Optional.empty();
    }

    private boolean emailExist(String email) {
        return userManagementRepository.findByEmail(email).isPresent();
    }
}
