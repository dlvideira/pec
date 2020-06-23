package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.model.Expense;
import com.pec.personalexpensescontrol.model.User;
import com.pec.personalexpensescontrol.repository.UserRepository;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class ExpenseService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Expense> getAllExpenses(String userId){
       var user =  userRepository.findById(userId);
       if (user.isPresent()){
           List<Expense> userExpenses = user.get().getExpenses();
           return userExpenses;
       }
        return null;
    }

    public boolean createExpense(String userId, Expense expenseBody) {
        //TODO usar optional se possivel
        Expense expense = new Expense();
        new ModelMapper().map(expenseBody, expense);
        expense.setExpenseCreatedDate(new DateTime());
        expense.setExpenseLastUpdatedDate(new DateTime());
        Criteria criteria = where("_id").is(userId)
                .and("expenses.expenseName")
                .ne(expense.getExpenseName());
        Update update = new Update().addToSet("expenses", expense);
        var response = mongoTemplate.updateFirst(Query.query(criteria), update, User.class);
        //TODO melhorar a validacao
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
                        var createdDate = item.getExpenseCreatedDate();
                        new ModelMapper().map(expenseBody, item);
                        item.setExpenseCreatedDate(createdDate);
                        item.setExpenseLastUpdatedDate(new DateTime());
                    });
            userRepository.save(user.get());
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public Optional deleteExpense(String userId, String expenseName) {
        var user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Expense> userExpenses = user.get().getExpenses();
            userExpenses.removeIf(item -> item.getExpenseName().equals(expenseName));
            userRepository.save(user.get());
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public Optional deleteAllExpenses(String userId) {
        var user = userRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setExpenses(null);
            userRepository.save(user.get());
            return Optional.of(user);
        }
        return Optional.empty();
    }
}