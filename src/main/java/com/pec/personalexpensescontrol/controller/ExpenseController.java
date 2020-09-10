package com.pec.personalexpensescontrol.controller;

import com.pec.personalexpensescontrol.model.Expense;
import com.pec.personalexpensescontrol.service.ExpenseService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses/{userId}")
    public ResponseEntity<?> getExpenses(@PathVariable("userId") String userId) {
        var response = expenseService.getAllExpenses(userId);
        if (!response.isEmpty()) {
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.badRequest().body("Não encontrei nenhum registro :(");
    }

    @PostMapping("/expenses/{userId}/createExpense")
    public ResponseEntity<String> createExpense(@PathVariable("userId") String userId, @RequestBody Expense expense) {
        try {
            expenseService.createExpense(userId, expense);
            return ResponseEntity.accepted().body("Despesa " + expense.getExpenseName() + " criada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/expenses/{userId}/updateExpense")
    public ResponseEntity<String> updateExpense(@PathVariable("userId") String userId, @RequestBody Expense expense) {
        var response = expenseService.updateExpense(userId, expense);
        if (response.isPresent()) {
            return ResponseEntity.ok().body("Despesa " + expense.getExpenseName() + " atualizada com sucesso!");
        }
        return ResponseEntity.badRequest().body("Não consegui atualizar a despesa " + expense.getExpenseName() + ":( \nTente novamente.");
    }

    @DeleteMapping("/expenses/{userId}/deleteExpense")
    public ResponseEntity<String> deleteExpense(@PathVariable("userId") String userId, @RequestParam("expenseId") ObjectId expenseId) {
        var response = expenseService.deleteExpense(userId, expenseId);
        if (response.isPresent()) {
            return ResponseEntity.ok().body("Despesa deletada com sucesso!");
        }
        return ResponseEntity.ok().body("Poxa, não consegui deletar a despesa.\nTente novamente.");
    }

    @DeleteMapping("/expenses/{userId}/deleteAllexpenses")
    public ResponseEntity<String> deleteAll(@PathVariable("userId") String userId) {
        var response = expenseService.deleteAllExpenses(userId);
        if (response.isPresent()) {
            return ResponseEntity.ok().body("Todas as despesas foram deletadas com sucesso!");
        }
        return ResponseEntity.badRequest().body("Poxa, não consegui deletar todas as despesas. :( \nTente novamente.");
    }
}