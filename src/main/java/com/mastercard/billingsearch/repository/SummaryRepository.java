package com.mastercard.billingsearch.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;

@Repository
public class SummaryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*@Query(" SELECT CD.SUMMARY_TRACE_ID,CD.INVOICE_DATE,CTT.BILLING_SUMMARY_TRACE_ID "
            + " FROM CHARGE_DETAIL CD, CHARGE_TRANSACTION_TRACE CTT "
            + " WHERE CD.SUMMARY_TRACE_ID = CTT.BILLING_SUMMARY_TRACE_ID and CD.INVOICE_DATE = :invoiceDate ")*/

    public Page<Object> findByAll(int pageId, int total) {
        String sql= "SELECT * FROM Charge_Detail LIMIT "+(pageId-1)+","+total;
        return new PageImpl<>(Collections.singletonList(jdbcTemplate.queryForList(sql)));
    }

}
