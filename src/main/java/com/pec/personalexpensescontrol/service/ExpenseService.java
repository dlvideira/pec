package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.model.Expense;
import com.pec.personalexpensescontrol.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Service
public class ExpenseService {

    @Autowired
    private UserRepository userRepository;

    public void createExpense(String userId, Expense expenseBody) {
        Expense expense = new Expense();
        new ModelMapper().map(expenseBody, expense);
        expense.setExpenseCreatedDate(new Date());
        expense.setExpenseLastUpdatedDate(new Date());

        var user = userRepository.findById(userId);
        user.setExpenses(Collections.singletonList(expense));
        user.setLastUpdatedDate(new Date());
        userRepository.save(user);
    }

    //TODO tem q validar se ja tem expenseName pro mesmo usuario


    public void updateExpense() {

    }

    public void deleteExpense() {

    }

    public void deleteAllExpenses() {

    }

}
