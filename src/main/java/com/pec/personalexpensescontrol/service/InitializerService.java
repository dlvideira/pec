package com.pec.personalexpensescontrol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(propagation = Propagation.REQUIRED)
public class InitializerService {
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private ExpenseService expenseService;

    public void initializeCollections(String userId) {
        bankAccountService.initializeBankAccounts(userId);
        expenseService.initializeExpenses(userId);
    }
}
