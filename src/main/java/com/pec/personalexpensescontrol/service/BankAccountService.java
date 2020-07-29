package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.model.BankAccount;
import com.pec.personalexpensescontrol.model.UserBankAccount;
import com.pec.personalexpensescontrol.repository.UserBankAccountRepository;
import org.bson.types.ObjectId;
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
public class BankAccountService {
    @Autowired
    private UserBankAccountRepository userBankAccountRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<BankAccount> getAllBankAccounts(String userId) {
        var userBankAccount = userBankAccountRepository.findByUserId(userId);
        if (userBankAccount.isPresent()){
            List<BankAccount> bankAccounts = userBankAccount.get().getBankAccounts();
            if (bankAccounts != null)
                return bankAccounts;
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    public void createBankAccount(String userId, BankAccount newBankAccount) throws Exception {
        if (bankAccountExist(userId, newBankAccount.getBankAccountId()).isPresent())
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
        var bankAccountToUpdateBalance = bankAccountExist(userId, bankAccount.getBankAccountId());
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

    public Optional<UserBankAccount> deleteBankAccount(String userId, ObjectId bankAccountId) {
        var bankAccount = bankAccountExist(userId, bankAccountId);
        if (bankAccount.isPresent()) {
            List<BankAccount> userBankAccounts = bankAccount.get().getBankAccounts();
            userBankAccounts.removeIf(item -> item.getBankAccountId().equals(bankAccountId));
            userBankAccountRepository.save(bankAccount.get());
            return bankAccount;
        }
        return Optional.empty();
    }

    public Optional<UserBankAccount> deleteAllBankAccounts(String userId) {
        var bankAccount = userBankAccountRepository.findByUserId(userId);
        if (bankAccount.isPresent()) {
            bankAccount.get().setBankAccounts(null);
            userBankAccountRepository.save(bankAccount.get());
            return bankAccount;
        }
        return Optional.empty();
    }

    private Optional<UserBankAccount> bankAccountExist(String userId, ObjectId bankAccountId) {
        return userBankAccountRepository.findByUserIdAndBankAccountsBankAccountId(userId, bankAccountId);
    }

    public void initializeBankAccounts(String userId) {
        userBankAccountRepository.save(new UserBankAccount(userId, null));
    }
}
