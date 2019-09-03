package com.mastercard.billingsearch.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SummaryResponse {

    private String summaryTraceId;
    private String invoiceDate;
    private String invNum;
    private String invoiceIca;
    private String activityIca;
    private String fromCntryCd;
    private String toCntryCd;
    private String geoCd;
    private String prdctId;
    private String eventSource;
    private String billEventId;
    private String tierEventId;
    private String servCd;
    private String collectionMethod;
    private String totalQty;
    private String totalAmt;
    private String chargeAmtLcl;
    private String currCd;
    private String chargeAmtUsd;
    private String feederId;
    private String processDate;
    private String billingSummaryTraceId;
    private String detailTraceId;
    private String billingEventId;
    private String recordCount;

}
