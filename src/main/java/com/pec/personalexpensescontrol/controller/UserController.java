package com.pec.personalexpensescontrol.controller;

import com.pec.personalexpensescontrol.infra.security.User;
import com.pec.personalexpensescontrol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/account/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody User user) {
        try {
            userService.createAccount(user);
            return ResponseEntity.accepted().body("Conta criada com sucesso!\nVocê receberá um e-mail com um link para ativar a sua conta.");
        } catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PatchMapping("/account/{userId}/updateUserEmail")
    public ResponseEntity<String> updateUserEmail(@PathVariable("userId") String userId, @RequestParam("newUserEmail") String newUserEmail) {
        try {
            var response = userService.updateEmail(userId, newUserEmail);
            if (response.isPresent()) {
                return ResponseEntity.ok().body("E-mail atualizado com sucesso!");
            }
            return ResponseEntity.ok().body("Não consegui atualizar o email agora,");
        } catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PatchMapping("/account/{userId}/updateUsername")
    public ResponseEntity<String> updateUserName(@PathVariable("userId") String userId, @RequestParam("newUsername") String newUsername) {
        var response = userService.updateUsername(userId, newUsername);
        if (response.isPresent()) {
            return ResponseEntity.ok().body("Nome de usuário atualizado com sucesso!");
        }
        return ResponseEntity.ok().body("Não consegui atualizar o nome de usuário agora,");
    }

    @PatchMapping("/account/{userId}/updateUserActive")
    public ResponseEntity<String> updateUserActiveStatus(@PathVariable("userId") String userId) {
        var response = userService.updateUserActiveStatus(userId);
        if (response.isPresent()) {
            return ResponseEntity.ok().body("Status atualizado com sucesso!");
        }
        return ResponseEntity.ok().body("Não consegui atualizar o status agora,");
    }

    @PostMapping("/account/{userId}/activate")
    public ResponseEntity<String> activateUser(@PathVariable("userId") String userId) {
        try {
            userService.activateUser(userId);
            return ResponseEntity.accepted().body("Usuário ativado com sucesso! \nAgora você pode fazer o login e começar a usar o PEC.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}