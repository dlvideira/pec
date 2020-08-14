package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.infra.security.User;
import com.pec.personalexpensescontrol.repository.UserManagementRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.pec.personalexpensescontrol.infra.security.User.create;

@Service
public class UserService {
    @Autowired
    private UserManagementRepository userManagementRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private InitializerService initializerService;

    public void createAccount(User newUserRequest) throws Exception {
        if (emailExist(newUserRequest.getEmail()))
            throw new Exception("Já existe uma conta associada com esse email.\nVocê esqueceu sua senha?");

        var user = create(
                newUserRequest.getUsername(),
                passwordEncoder.encode(newUserRequest.getPassword()),
                newUserRequest.getEmail(),
                newUserRequest.getRole(),
                false);
        var createdUser = userManagementRepository.save(user);
    }

    public Optional<User> updateEmail(String userId, String newUserEmail) throws Exception {
        if (emailExist(newUserEmail))
            throw new Exception("Já existe uma conta associada com esse email.");

        var user = userManagementRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setEmail(newUserEmail);
            userManagementRepository.save(user.get());
            return user;
        }
        return Optional.empty();
    }

    public Optional<User> updateUsername(String userId, String newUsername) {
        var user = userManagementRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setUsername(newUsername);
            userManagementRepository.save(user.get());
            return user;
        }
        return Optional.empty();
    }

    @Deprecated
    public Optional<User> updateUserActiveStatus(String userId) {
        var user = userManagementRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setActive(!user.get().isActive());
            userManagementRepository.save(user.get());
            return user;
        }
        return Optional.empty();
    }

    public void activateUser(String userId) throws Exception {
        var user = userManagementRepository.findById(userId);
        if (user.isPresent()) {
            if (!user.get().isActive()) {
                user.get().setActive(true);
                initializerService.initializeCollections(userId);
                userManagementRepository.save(user.get());
            } else throw new Exception("Usuário já está ativo.\nSe você esqueceu a sua senha, clique em 'Esqueci minha senha'");
        } else throw new NotFoundException("Usuário não encontrado.");

    }

    public boolean emailExist(String email) {
        return userManagementRepository.findByEmail(email).isPresent();
    }
}
