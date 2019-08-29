package com.mastercard.billingsearch.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SummaryModel {

    @NotBlank
    private String invoiceDate;
    private String billingEvent;
    private String invoiceNumber;
    private String activityICA;
    private String feederType;
    private int page;
}

