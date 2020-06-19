package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.model.Expense;
import com.pec.personalexpensescontrol.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.Date;

@Service
public class ExpenseService {

    @Autowired
    private UserRepository userRepository;

    public void createExpense(String userId, Expense expenseBody) {
        var user = userRepository.findById(userId);
        Expense expense = new Expense();
        new ModelMapper().map(expenseBody, expense);
        expense.setExpenseCreatedDate(new Date());
        expense.setExpenseLastUpdatedDate(new Date());
        user.setExpense(expense);
        user.setLastUpdatedDate(new Date());
        userRepository.save(user);
    }
}
