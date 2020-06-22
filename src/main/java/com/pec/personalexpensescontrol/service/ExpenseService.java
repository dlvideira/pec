package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.model.Expense;
import com.pec.personalexpensescontrol.model.User;
import com.pec.personalexpensescontrol.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class ExpenseService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean createExpense(String userId, Expense expenseBody) {
        //TODO usar optional se possivel
        //TODO melhorar a validacao
        Expense expense = new Expense();
        new ModelMapper().map(expenseBody, expense);
        expense.setExpenseCreatedDate(new Date());
        expense.setExpenseLastUpdatedDate(new Date());
        Criteria criteria = where("_id").is(userId)
                .and("expenses.expenseName")
                .ne(expense.getExpenseName());
        Update update = new Update();
        update.addToSet("expenses", expense);
        var response = mongoTemplate.updateFirst(Query.query(criteria), update, User.class);
        return response.getMatchedCount() > 0;
    }

    public Optional updateExpense(String userId, Expense expenseBody) {
        var user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Expense> userExpenses = user.get().getExpenses();
            userExpenses.stream()
                    .filter(item -> item.getExpenseName().equals(expenseBody.getExpenseName()))
                    .findFirst()
                    .ifPresent(item -> {
                        //TODO tentar um mapper (item, expenseBody)
                        item.setAmount(expenseBody.getAmount());
                        item.setExpenseLastUpdatedDate(new Date());
                        item.setCategory(expenseBody.getCategory());
                        item.setCurrentParcel(expenseBody.getCurrentParcel());
                        item.setTotalParcels(expenseBody.getTotalParcels());
                        item.setFrequency(expenseBody.getFrequency());
                    });
            userRepository.save(user.get());
            return Optional.of(new User());
        }
        return Optional.empty();
    }

    public Optional deleteExpense(String userId, String expenseName) {
        var user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Expense> userExpenses = user.get().getExpenses();
            userExpenses.removeIf(item -> item.getExpenseName().equals(expenseName));
            userRepository.save(user.get());
            return Optional.of(new User());
        }
        return Optional.empty();
    }

    public Optional deleteAllExpenses(String userId) {
        //TODO n'ao esta deletando, esta criando uma nova vazia
        var user = userRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setExpenses(Collections.singletonList(new Expense()));
            userRepository.save(user.get());
            return Optional.of(new User());
        }
        return Optional.empty();
    }
}
