package com.pec.personalexpensescontrol.controller;

import com.pec.personalexpensescontrol.model.Expense;
import com.pec.personalexpensescontrol.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses/{userId}")
    public ResponseEntity getExpenses(String userId) {
        return null;
    }

    @PostMapping("/expenses/{userId}/createExpense")
    public ResponseEntity createExpense(@PathVariable("userId") String userId, @RequestBody Expense expense) {
        try {
            expenseService.createExpense(userId, expense);
            return ResponseEntity.ok().body("Despesa criada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não foi possível criar a despesa :(");
        }
    }

}
