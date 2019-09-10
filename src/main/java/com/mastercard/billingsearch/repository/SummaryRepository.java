package com.mastercard.billingsearch.repository;


import com.mastercard.billingsearch.entity.SummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SummaryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<SummaryResponse> findPageableRecords(String sqlQuery) {
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(SummaryResponse.class));
    }

    @Transactional(readOnly=true)
    public List<String> findAllInvoiceDate() {
        String sqlQuery = "select distinct to_char(invoice_date, 'MM/DD/YYYY') from charge_detail where invoice_date "
                + "between add_months(trunc(sysdate, 'month'), -12) and trunc(sysdate, 'month') "
                + "order by to_char(invoice_date, 'MM/DD/YYYY') desc";
        return jdbcTemplate.queryForList(sqlQuery, String.class);
    }

    void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate   = jdbcTemplate;
    }

    @Transactional(readOnly=true)
    public List<String> findAllActivityICA() {
        String sqlQuery = "select distinct activity_ica from charge_detail order by activity_ica desc";
        return jdbcTemplate.queryForList(sqlQuery, String.class);
    }

    @Transactional(readOnly=true)
    public List<String> findAllFeederType() {
        String sqlQuery = "select distinct feeder_id from charge_transaction_trace order by feeder_id desc";
        return jdbcTemplate.queryForList(sqlQuery, String.class);
    }
}
