package com.mastercard.billingsearch.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SummaryModel {

    @NotNull
    private final String invoiceDate;
    private final String billingEvent;
    private final String invoiceNumber;
    private final String activityICA;
    private final String feederType;
    @Min(1)
    private final int page;
}

