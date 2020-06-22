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

import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class ExpenseService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public void createExpense(String userId, Expense expenseBody) {
        //TODO usar optional se possivel
        //TODO dar um jeito de capturar o retorno e mandar para o controller
        Expense expense = new Expense();
        new ModelMapper().map(expenseBody, expense);
        expense.setExpenseCreatedDate(new Date());
        expense.setExpenseLastUpdatedDate(new Date());
        Criteria criteria = where("_id").is(userId)
                .and("expenses.expenseName")
                .ne(expense.getExpenseName());
        Update update = new Update();
        update.addToSet("expenses", expense);
        mongoTemplate.updateFirst(Query.query(criteria), update, User.class);
    }

    public Optional updateExpense(String userId, Expense expenseBody) {
        var user = userRepository.findById(userId);
        if(user.isPresent()) {
            List<Expense> userExpenses = user.get().getExpenses();
            userExpenses.stream()
                    .filter(item -> item.getExpenseName().equals(expenseBody.getExpenseName()))
                    .findFirst()
                    .ifPresent(item -> {
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
        if(user.isPresent()){
            List<Expense> userExpenses = user.get().getExpenses();
            userExpenses.removeIf(item -> item.getExpenseName().equals(expenseName));
            userRepository.save(user.get());
            return Optional.of(new User());
        }
        return Optional.empty();
    }

    public Optional deleteAllExpenses(String userId) {
        //TODO n'ao esta deltando, esta criando uma nova vazia
        var user = userRepository.findById(userId);
        if(user.isPresent()){
            user.get().setExpenses(Collections.singletonList(new Expense()));
            userRepository.save(user.get());
            return Optional.of(new User());
        }
        return Optional.empty();

    }

}
