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
            // TODO mongoTemplate sempre retorna 200, validar response
            return ResponseEntity.ok().body("Despesa " + expense.getExpenseName() + " criada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não foi possível criar a despesa "  + expense.getExpenseName() + ":(");
        }
    }

    @PostMapping("/expenses/{userId}/updateExpense")
    public ResponseEntity updateExpense(@PathVariable("userId") String userId, @RequestBody Expense expense){
        try {
            //TODO tratar response
            //expenseService.updateExpense(userId, expense);
            return ResponseEntity.ok().body(expenseService.updateExpense(userId, expense));
           // return ResponseEntity.ok().body("Despesa " + expense.getExpenseName() + " atualizada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.ok().body(expenseService.updateExpense(userId, expense));

            //return ResponseEntity.badRequest().body("Não foi possível atualizar a despesa "  + expense.getExpenseName() + ":(");
        }
    }
    @DeleteMapping("/expenses/{userId}/deleteExpense/{expenseName}")
    public ResponseEntity deleteExpense(@PathVariable("userId") String userId, @PathVariable("expenseName") String expenseName){
        try {
            //TODO tratar response
            //expenseService.updateExpense(userId, expense);
            return ResponseEntity.ok().body(expenseService.deleteExpense(userId, expenseName));
            // return ResponseEntity.ok().body("Despesa " + expense.getExpenseName() + " atualizada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.ok().body(expenseService.deleteExpense(userId, expenseName));

            //return ResponseEntity.badRequest().body("Não foi possível atualizar a despesa "  + expense.getExpenseName() + ":(");
        }
    }

    @DeleteMapping("/expenses/{userId}/deleteAll")
    public ResponseEntity deleteAll(@PathVariable("userId") String userId){
        var response = expenseService.deleteAllExpenses(userId);
        if (response.isPresent()){
            return ResponseEntity.ok().body("Todas as despesas foram deletadas com sucesso");
        } return ResponseEntity.ok().body("Poxa, não consegui deletar todas as despesas. :( \nTente novamente");
    }
}
