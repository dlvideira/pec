package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.model.BankAccount;
import com.pec.personalexpensescontrol.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void createBankAccount(BankAccount newBankAccount) throws Exception {
        if (bankAccountExist(newBankAccount.getBankAccount()).isPresent())
            throw new Exception("Conta bancária já existe para este usuário");
        //TODO abstrair as exceptions quando criarmos a classe de exception

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankName(newBankAccount.getBankName());
        bankAccount.setBankAgency(newBankAccount.getBankAgency());
        bankAccount.setBankAccount(newBankAccount.getBankAccount());
        bankAccount.setAccountBalance(new BigDecimal(0));
        bankAccount.setBalanceUpdatedDate(new Date());
        bankAccountRepository.save(bankAccount);
    }

    private Optional bankAccountExist(String bankAccount) {
        return bankAccountRepository.findByBankAccount(bankAccount);
    }
}
