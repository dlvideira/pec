package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.infra.security.User;
import com.pec.personalexpensescontrol.repository.UserManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        //TODO criar errorHandling class para Exception
        var user = create(
                newUserRequest.getUserName(),
                passwordEncoder.encode(newUserRequest.getPassword()),
                newUserRequest.getEmail(),
                newUserRequest.getRole(),
                true);

        userManagementRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userManagementRepository.findByEmail(email).isPresent();
    }
}
