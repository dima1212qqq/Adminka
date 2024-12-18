package com.example.application.model;

import lombok.Data;

@Data
public class CliDto {
    private Long id;
    private String companyName;
    private String contactPhone;
    private String balance;
    private String legalAddress;
    private String tariff;
    private String telegram;
    private Boolean activeTariff;
    private String tariffEndDate;
    private TypeExpense type;

    enum TypeExpense{
        PROMO, DAILY, MONTHLY, NOT, YEARLY;
    }
}

