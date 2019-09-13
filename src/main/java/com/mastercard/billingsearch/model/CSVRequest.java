package com.mastercard.billingsearch.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CSVRequest {
    @NotNull
	private String invoiceDate;
    @NotNull
	private String billEventId;
    @NotNull
	private String invoiceNumber;
    @NotNull
	private String activityICA;
    @NotNull
	private String userType;
    @NotNull
	private String feederType;

	
}