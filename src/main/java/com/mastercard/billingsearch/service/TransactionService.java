package com.mastercard.billingsearch.service;

import com.mastercard.billingsearch.model.UserRoles;

import java.util.List;
import java.util.Map;

public interface TransactionService {
	
	public List<Map<String, Object>> billingTransactionDetails(String feederType, String userId, int totalRecords);
	
	public List<UserRoles> elementMappingDetails(String userId, String feederType);

}