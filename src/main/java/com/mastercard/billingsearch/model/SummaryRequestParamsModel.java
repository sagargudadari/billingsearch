package com.mastercard.billingsearch.model;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class SummaryRequestParamsModel {

    @NotBlank
    private String invoiceDate;
    private String userType;
    private String billingEvent;
    private String invoiceNumber;
    private String activityICA;
    private String userName;
}

