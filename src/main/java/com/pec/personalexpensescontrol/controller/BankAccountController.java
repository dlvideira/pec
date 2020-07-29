package com.pec.personalexpensescontrol.controller;

import com.pec.personalexpensescontrol.model.BankAccount;
import com.pec.personalexpensescontrol.service.BankAccountService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/bankAccount/{userId}")
    public ResponseEntity getBankAccounts(@PathVariable("userId") String userId) {
        var response = bankAccountService.getAllBankAccounts(userId);
        if (!response.isEmpty()) {
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.badRequest().body("Não encontrei nenhum registro :(");
    }

    @PostMapping("/bankAccount/{userId}/createBankAccount")
    public ResponseEntity createBankAccount(@PathVariable("userId") String userId,
                                            @RequestBody BankAccount BankAccount) {
        try {
            bankAccountService.createBankAccount(userId, BankAccount);
            return ResponseEntity.accepted().body("Conta bancária criada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PatchMapping("/bankAccount/{userId}/updateBankBalance")
    public ResponseEntity updateBankAccountBalance(@PathVariable("userId") String userId,
                                                   @RequestBody BankAccount bankAccount) {
        try {
            bankAccountService.updateBankAccountBalance(userId, bankAccount);
            return ResponseEntity.accepted().body("Valor atualizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/bankAccount/{userId}/deleteBankAccount")
    public ResponseEntity deleteBankAccount(@PathVariable("userId") String userId,
                                            @RequestParam("bankAccountId") ObjectId bankAccountId) {
        try {
            bankAccountService.deleteBankAccount(userId, bankAccountId);
            return ResponseEntity.accepted().body("Conta bancária deletada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não consegui deletar as sua conta bancária \nTente novamente.");
        }
    }

    @DeleteMapping("/bankAccount/{userId}/deleteAllBankAccounts")
    public ResponseEntity deleteBankAccount(@PathVariable("userId") String userId) {
        try {
            bankAccountService.deleteAllBankAccounts(userId);
            return ResponseEntity.accepted().body("Todas as contas bancárias foram deletadas com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não consegui deletar as contas bancárias \nTente novamente.");
        }
    }
}

