package com.mastercard.billingsearch.service.impl;

import com.mastercard.billingsearch.model.UserRoles;
import com.mastercard.billingsearch.repository.TransactionRepository;
import com.mastercard.billingsearch.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	private String sqlQueryToFetchRoleName = "SELECT role_name FROM USER_ROLES WHERE user_id=?";

	@Override
	public List<Map<String, Object>> billingTransactionDetails(String feederType, String userId,
															   int totalRecords) {
		return transactionRepository.getBillingTransactionDetails(feederType,elementMappingDetails(userId, feederType)
				, totalRecords);
	}

	@Override
	public List<UserRoles> elementMappingDetails(String userId, String feederType) {
		return transactionRepository.getElementMappingDetails(getRoleName(userId), feederType);
	}

	public String getRoleName(String userId) {
		return jdbcTemplate.queryForObject(sqlQueryToFetchRoleName, new Object[] { userId }, String.class);
	}

}