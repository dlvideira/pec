package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.model.BankAccount;
import com.pec.personalexpensescontrol.model.UserBankAccount;
import com.pec.personalexpensescontrol.repository.UserBankAccountRepository;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class BankAccountService {
    @Autowired
    private UserBankAccountRepository userBankAccountRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<BankAccount> getAllBankAccounts(String userId) {
        var userBankAccount = userBankAccountRepository.findByUserId(userId);
        if (userBankAccount.isPresent()) {
            List<BankAccount> bankAccounts = userBankAccount.get().getBankAccounts();
            return bankAccounts != null ? bankAccounts : new ArrayList<>();
        }
        return new ArrayList<>();
    }

    public void createBankAccount(String userId, BankAccount newBankAccount) throws Exception {
        if (bankAccountExist(userId, newBankAccount.getBankAccountId()).isPresent())
            throw new Exception("Conta bancária já existe para este usuário");

        BankAccount bankAccount = new BankAccount();
        new ModelMapper().map(newBankAccount, bankAccount);
        Criteria criteria = where("userId").is(userId);
        Update update = new Update().addToSet("bankAccounts", bankAccount);
        mongoTemplate.updateFirst(query(criteria), update, UserBankAccount.class);
    }

    public void updateBankAccountBalance(String userId, BankAccount bankAccount) throws Exception {
        var bankAccountToUpdateBalance = bankAccountExist(userId, bankAccount.getBankAccountId());
        if (bankAccountToUpdateBalance.isPresent()) {
            bankAccountToUpdateBalance.get().getBankAccounts().stream()
                    .filter(item -> item.getBankAccountId().equals(bankAccount.getBankAccountId()))
                    .findFirst()
                    .ifPresent(item -> {
                        item.setBankAccountLastUpdatedDate(new Date());
                        item.setAccountBalance(item.getAccountBalance().add(bankAccount.getAccountBalance()));
                    });
            userBankAccountRepository.save(bankAccountToUpdateBalance.get());
        } else throw new Exception("Conta bancária não existe");
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
