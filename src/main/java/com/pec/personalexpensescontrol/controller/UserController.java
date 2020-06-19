package com.pec.personalexpensescontrol.controller;

import com.pec.personalexpensescontrol.model.User;
import com.pec.personalexpensescontrol.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/account/createAccount")
    public ResponseEntity createAccount(@RequestBody User newUser){
        try {
            userService.createAccount(newUser);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
