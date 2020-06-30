package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.infra.security.Role;
import com.pec.personalexpensescontrol.infra.security.User;
import com.pec.personalexpensescontrol.repository.UserManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserManagementRepository userManagementRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createAccount(User newUser) throws Exception {
        if (emailExist(newUser.getEmail()))
            throw new Exception("Já existe uma conta associada com esse email.\nVocê esqueceu seu usuário?");
        //TODO criar errorHandling class para Exception


        User user = new User();
        user.setUserName(newUser.getUserName());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setEmail(newUser.getEmail());
        user.setRole(Role.USER);
        user.setActive(true);
      //  var user = User.create(newUser.getUserName(), newUser.getPassword(), newUser.getEmail(), newUser.getRole(), newUser.isActive());

        userManagementRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userManagementRepository.findByEmail(email).isPresent();
    }
}
