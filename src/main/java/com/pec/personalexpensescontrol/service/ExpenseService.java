package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.model.Expense;
import com.pec.personalexpensescontrol.model.UserExpense;
import com.pec.personalexpensescontrol.repository.UserExpenseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class ExpenseService {
    @Autowired
    private UserExpenseRepository userExpenseRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Expense> getAllExpenses(String userId) {
        var user = userExpenseRepository.findById(userId);
        if (user.isPresent()) {
            List<Expense> userExpenses = user.get().getExpenses();
            if (userExpenses != null)
                return userExpenses;
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    public boolean createExpense(String userId, Expense expenseBody) throws Exception {
        if (expenseNameExist(userId, expenseBody.getExpenseName()))
            throw new Exception("Já existe uma despesa com esse nome.");
        //TODO jogar na errorHandling class para Exception
        Expense expense = new Expense();
        new ModelMapper().map(expenseBody, expense);
        expense.setExpenseCreatedDate(new Date());
        expense.setExpenseLastUpdatedDate(new Date());
        Criteria criteria = where("_id").is(userId);
        Update update = new Update().addToSet("expenses", expense);
        var response = mongoTemplate.updateFirst(Query.query(criteria), update, UserExpense.class);
        return response.getMatchedCount() > 0;
    }

    public Optional updateExpense(String userId, Expense expenseBody) {
        var user = userExpenseRepository.findById(userId);
        if (user.isPresent()) {
            List<Expense> userExpenses = user.get().getExpenses();
            userExpenses.stream()
                    .filter(item -> item.getExpenseName().equals(expenseBody.getExpenseName()))
                    .findFirst()
                    .ifPresent(item -> {
                        var createdDate = item.getExpenseCreatedDate();
                        new ModelMapper().map(expenseBody, item);
                        item.setExpenseCreatedDate(createdDate);
                        item.setExpenseLastUpdatedDate(new Date());
                    });
            userExpenseRepository.save(user.get());
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public Optional deleteExpense(String userId, String expenseName) {
        var user = userExpenseRepository.findById(userId);
        if (user.isPresent()) {
            List<Expense> userExpenses = user.get().getExpenses();
            userExpenses.removeIf(item -> item.getExpenseName().equals(expenseName));
            userExpenseRepository.save(user.get());
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public Optional deleteAllExpenses(String userId) {
        var user = userExpenseRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setExpenses(null);
            userExpenseRepository.save(user.get());
            return Optional.of(user);
        }
        return Optional.empty();
    }

    private boolean expenseNameExist(String userId, String expenseName) {
        return userExpenseRepository.findByIdAndExpensesExpenseName(userId, expenseName).isPresent();
    }
}