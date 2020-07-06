package com.pec.personalexpensescontrol.controller;

import com.pec.personalexpensescontrol.model.BankAccount;
import com.pec.personalexpensescontrol.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;





import java.math.BigDecimal;


@RestController
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping("/bankAccount/createBankAccount")
    public ResponseEntity createBankAccount(@RequestBody BankAccount bankAccount) {
        try {
            bankAccountService.createBankAccount(bankAccount);
            return ResponseEntity.accepted().body("Conta bancária criada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PatchMapping("/bankAccount/{bankAccountId}/updateBankBalance")
    public ResponseEntity updateBankAccountBalance(@PathVariable("bankAccountId") String bankAccountId, @RequestBody BankAccount bankAccount){
        try {
            bankAccountService.updateBankAccountBalance(bankAccountId, bankAccount.getAccountBalance());
            return ResponseEntity.accepted().body("Valor atualizado com sucesso!");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
