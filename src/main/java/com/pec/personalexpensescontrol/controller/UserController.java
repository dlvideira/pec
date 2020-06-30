package com.pec.personalexpensescontrol.controller;

import com.pec.personalexpensescontrol.infra.security.User;
import com.pec.personalexpensescontrol.service.UserService;
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
    public ResponseEntity createAccount(@RequestBody User user) {
        try {
            userService.createAccount(user);
            return ResponseEntity.accepted().body("Conta criada com sucesso!\nAgora você pode fazer login e começar a usar o PEC :)");
        } catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
}
