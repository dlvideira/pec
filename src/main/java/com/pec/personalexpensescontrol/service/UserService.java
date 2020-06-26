package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.model.User;
import com.pec.personalexpensescontrol.repository.UserManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserManagementRepository userManagementRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createAccount(User newUser) throws Exception {
        if(emailExist(newUser.getEmail()).isPresent())
            throw new Exception("Já existe uma conta associada com esse email.\nVocê esqueceu seu usuário?");

        User user = new User();
                user.setUserName(newUser.getUserName());
                user.setPassword(passwordEncoder.encode(newUser.getPassword()));
                user.setEmail(newUser.getEmail());
        userManagementRepository.save(user);
    }

    private Optional emailExist(String email) {
        return userManagementRepository.findByEmail(email);
    }
}
