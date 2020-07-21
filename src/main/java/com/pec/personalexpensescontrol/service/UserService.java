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
    private UserManagementRepository userManagementRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private BankAccountService bankAccountService;

    public void createAccount(User newUserRequest) throws Exception {
        if (newUserRequest == null)
            throw new Exception("É necessário preencher o formulário de criação de usuário");
        if (emailExist(newUserRequest.getEmail()))
            throw new Exception("Já existe uma conta associada com esse email.\nVocê esqueceu sua senha?");

        var user = create(
                newUserRequest.getUsername(),
                passwordEncoder.encode(newUserRequest.getPassword()),
                newUserRequest.getEmail(),
                newUserRequest.getRole(),
                true);
        var createdUser = userManagementRepository.save(user);
        expenseService.initializeExpenses(createdUser.getId());
        bankAccountService.initializeBankAccounts(createdUser.getId());
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

    public Optional<User> updateUserActiveStatus(String userId) {
        var user = userManagementRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setActive(!user.get().isActive());
            userManagementRepository.save(user.get());
            return user;
        }
        return Optional.empty();
    }

    public boolean emailExist(String email) {
        return userManagementRepository.findByEmail(email).isPresent();
    }
}
