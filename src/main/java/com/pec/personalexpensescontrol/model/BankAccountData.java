package com.pec.personalexpensescontrol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountData {
    private String bankName;
    private String bankAgency;
    private String bankAccount;
    private BigDecimal accountBalance;
    private DateTime balanceUpdatedDate;

}
