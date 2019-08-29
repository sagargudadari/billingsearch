package com.mastercard.billingsearch.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SummaryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*@Query(" SELECT CD.SUMMARY_TRACE_ID,CD.INVOICE_DATE,CTT.BILLING_SUMMARY_TRACE_ID "
            + " FROM CHARGE_DETAIL CD, CHARGE_TRANSACTION_TRACE CTT "
            + " WHERE CD.SUMMARY_TRACE_ID = CTT.BILLING_SUMMARY_TRACE_ID and CD.INVOICE_DATE = :invoiceDate ")*/

    public List<Map<String, Object>> findByAll() {
        return jdbcTemplate.queryForList("SELECT * FROM Charge_Detail");
    }

}
