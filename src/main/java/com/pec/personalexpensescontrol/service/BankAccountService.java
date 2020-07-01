package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.model.BankAccount;
import com.pec.personalexpensescontrol.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void createBankAccount(BankAccount newBankAccount) throws Exception {
        if (bankAccountExist(newBankAccount.getBankAccountNumber(), newBankAccount.getBankName()).isPresent())
            throw new Exception("Conta bancária já existe para este usuário");
        //TODO abstrair as exceptions quando criarmos a classe de exception

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankName(newBankAccount.getBankName());
        bankAccount.setBankAgency(newBankAccount.getBankAgency());
        bankAccount.setBankAccountNumber(newBankAccount.getBankAccountNumber());
        bankAccount.setAccountBalance(new BigDecimal(0));
        bankAccountRepository.save(bankAccount);
    }

    public void updateBankAccountBalance(String bankAccountId, BigDecimal value) {
        var bankAccountToUpdateBalance = bankAccountRepository.findById(bankAccountId);
        if (bankAccountToUpdateBalance.isPresent()) {
            BigDecimal balance = bankAccountToUpdateBalance.get().getAccountBalance();
            balance = balance.add(value);

            bankAccountToUpdateBalance.get().setAccountBalance(balance);
            bankAccountRepository.save(bankAccountToUpdateBalance.get());
        }
    }

    private Optional bankAccountExist(String bankAccount, String bankName) {
        return bankAccountRepository.findByBankAccountNumberAndBankName(bankAccount, bankName);
    }
}
