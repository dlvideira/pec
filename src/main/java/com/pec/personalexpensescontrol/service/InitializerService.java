package com.pec.personalexpensescontrol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Initializer {
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private ExpenseService expenseService;

    public void initializeCollections(String userId){

        bankAccountService.initializeBankAccounts(userId);
        expenseService.initializeExpenses(userId);
    }
}
