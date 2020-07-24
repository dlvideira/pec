package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.model.BankAccount;
import com.pec.personalexpensescontrol.model.UserBankAccount;
import com.pec.personalexpensescontrol.repository.UserBankAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class BankAccountService {
    @Autowired
    private UserBankAccountRepository userBankAccountRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public void createBankAccount(String userId, BankAccount newBankAccount) throws Exception {
        if (bankAccountExist(userId, newBankAccount.getBankAccountNumber()))
            throw new Exception("Conta bancária já existe para este usuário");

        BankAccount bankAccount = new BankAccount();
        new ModelMapper().map(newBankAccount, bankAccount);
        bankAccount.setBankAccountCreatedDate(new Date());
        bankAccount.setBankAccountLastUpdatedDate(new Date());
        Criteria criteria = where("userId").is(userId);
        Update update = new Update().addToSet("bankAccounts", bankAccount);
        mongoTemplate.updateFirst(Query.query(criteria), update, UserBankAccount.class);
    }

    public void updateBankAccountBalance(String userId, BankAccount bankAccount) throws Exception {
        var bankAccountToUpdateBalance = bankAccountExist(userId, bankAccount);
        if (bankAccountToUpdateBalance.isEmpty())
            throw new Exception("Conta bancária não existe");

        List<BankAccount> bankAccounts = bankAccountToUpdateBalance.get().getBankAccounts();
        bankAccounts.stream()
                .filter(item -> item.getBankAccountId().equals(bankAccount.getBankAccountId()))
                .findFirst()
                .ifPresent(item -> {
                    var createdDate = item.getBankAccountCreatedDate();
                    item.setBankAccountCreatedDate(createdDate);
                    item.setBankAccountLastUpdatedDate(new Date());
                    item.setAccountBalance(item.getAccountBalance().add(bankAccount.getAccountBalance()));
                });
        userBankAccountRepository.save(bankAccountToUpdateBalance.get());

    }

    private Optional<UserBankAccount> bankAccountExist(String userId, BankAccount bankAccount) {
        return userBankAccountRepository.findByUserIdAndBankAccountsBankAccountId(userId, bankAccount.getBankAccountId());
    }

    private boolean bankAccountExist(String userId, String bankAccountNumber) {
        return userBankAccountRepository.findByUserIdAndBankAccountsBankAccountNumber(userId, bankAccountNumber).isPresent();
    }

    public void initializeBankAccounts(String userId) {
        userBankAccountRepository.save(new UserBankAccount(userId, null));
    }
}
