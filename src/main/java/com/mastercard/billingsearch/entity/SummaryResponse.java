package com.mastercard.billingsearch.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SummaryResponse {

    private String summaryTraceId;
    private String invoiceDate;
    @JsonProperty("invoiceNumber")
    private String invNum;
    private String invoiceIca;
    private String activityIca;
    @JsonProperty("fromCountryCode")
    private String fromCntryCd;
    @JsonProperty("toCountryCode")
    private String toCntryCd;
    @JsonProperty("geoCode")
    private String geoCd;
    @JsonProperty("productId")
    private String prdctId;
    private String eventSource;
    private String billEventId;
    private String tierEventId;
    @JsonProperty("serviceCode")
    private String servCd;
    private String collectionMethod;
    private String totalQty;
    private String totalAmt;
    @JsonProperty("chargeAmountLocal")
    private String chargeAmtLcl;
    @JsonProperty("currencyCode")
    private String currCd;
    @JsonProperty("chargeAmountUsd")
    private String chargeAmtUsd;
    private String feederId;
    private String processDate;
    private String billingSummaryTraceId;
    private String detailTraceId;
    private String billingEventId;
    private String recordCount;

}
