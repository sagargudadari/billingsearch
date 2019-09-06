package com.mastercard.billingsearch.repository;

import com.mastercard.billingsearch.entity.SummaryResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SummaryRepositoryTest {

    @InjectMocks
    SummaryRepository summaryRepository;

    @Mock
    JdbcTemplate jdbcTemplate;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        summaryRepository.setJdbcTemplate(jdbcTemplate);
    }

    @Test
    public void findPageableRecordsTest() {

        when(jdbcTemplate.query(anyString(), (BeanPropertyRowMapper<SummaryResponse>) any())).thenReturn(new ArrayList<>());

        String sqlQuery = "select * from charge_detail cd, charge_transaction_trace ctt where cd.summary_trace_id = ctt.billing_summary_trace_id and cd.invoice_date <= to_date('2019-12-1', 'yyyy/mm/dd') order by invoice_date desc offset nvl(1-1,1)*1 rows fetch next 1 rows only";
        summaryRepository.findPageableRecords(sqlQuery);

        verify(jdbcTemplate, atLeastOnce()).query(anyString(), (BeanPropertyRowMapper<SummaryResponse>) any());
    }
}
