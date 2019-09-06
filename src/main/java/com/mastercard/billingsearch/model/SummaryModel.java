package com.mastercard.billingsearch.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class SummaryModel {

    @NotNull
    private String invoiceDate;
    private String billingEvent;
    private String invoiceNumber;
    private String activityICA;
    private String feederType;
    @Min(1)
    private int page;

}

