package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.model.User;
import com.pec.personalexpensescontrol.repository.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void createAccount(User newUser){
        User user = new User();
        user.setUserName(newUser.getUserName());
        userRepository.save(user);
    }
}
