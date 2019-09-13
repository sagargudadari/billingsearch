package com.mastercard.billingsearch.repository;

import com.mastercard.billingsearch.constant.Constant;
import com.mastercard.billingsearch.model.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TransactionRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private String sqlQueryToFetchRoleName="SELECT ELEMENTS, AS_FIELDS FROM ELEMENT_MAPPING WHERE role=? AND FEEDER_TYPE=? AND ENABLE='Y'";

	
	public List<Map<String, Object>> getBillingTransactionDetails(String feederType, List<UserRoles> elementMappingDetail,
																  int totalRecords) {
		// TODO if detailFields and asFields are NULL
		Object[] detailFields = elementMappingDetail.stream().map(e->e.getElements()).toArray();
		Object[] asFields = elementMappingDetail.stream().map(e->e.getAsFields()).toArray();
		String buildAsQuery = buildAsQuery(detailFields, asFields);
		String buildTableNamesUingFeederType = buildTableNamesUingFeederType(feederType);
		String sqlQuery = "SELECT " + buildAsQuery + " FROM " + buildTableNamesUingFeederType
				+ " WHERE EVENT.IME_TRACE_ID = TRANSACTION.IME_TRACE_ID";
		jdbcTemplate.setMaxRows(totalRecords);
		return jdbcTemplate.queryForList(sqlQuery);
	}

	// auth
	private String buildTableNamesUingFeederType(String feederType) {
		StringBuilder feederTypeStr = new StringBuilder(feederType);// is it pos to add table type in event detail
		feederTypeStr.append(Constant.BILLING_EVENT_DETAIL).append(Constant.COMMA)
				.append(feederType + Constant.TRANSACTION_DETAIL);
		return feederTypeStr.toString();
	}

	private String buildAsQuery(Object[] detailFields, Object[] asFields) {
		StringBuilder stringBuilder = new StringBuilder();

		if (detailFields.length == asFields.length) {
			for (int i = 0; i < asFields.length; i++) {
				Object asField =  asFields[i];
				Object detailField =  detailFields[i];
				stringBuilder.append(detailField).append(Constant.AS).append(asField);
				if (i != asFields.length - 1) {
					stringBuilder.append(Constant.COMMA);
				}
			}
			return stringBuilder.toString();
		}

		return "*";

	}
	

	public List<UserRoles> getElementMappingDetails(String roleName, String feederType) {
	
		 return jdbcTemplate.query(
	                sqlQueryToFetchRoleName,
	                new Object[]{roleName,feederType},
	                new BeanPropertyRowMapper<UserRoles>(UserRoles.class)
	        ); 
	}


}