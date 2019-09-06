package com.mastercard.billingsearch.repository;


import com.mastercard.billingsearch.entity.SummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SummaryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<SummaryResponse> findPageableRecords(String sqlQuery) {
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(SummaryResponse.class));
    }

    void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
