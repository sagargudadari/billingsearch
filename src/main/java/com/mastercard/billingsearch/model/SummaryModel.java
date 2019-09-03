package com.mastercard.billingsearch.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
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

