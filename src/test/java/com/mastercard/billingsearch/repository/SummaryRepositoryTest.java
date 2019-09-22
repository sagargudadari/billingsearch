package com.mastercard.billingsearch.repository;

import com.mastercard.billingsearch.entity.SummaryResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SummaryRepositoryTest {

    @InjectMocks
    SummaryRepository summaryRepository;

    @Mock
    JdbcTemplate jdbcTemplate;

    @Captor
    ArgumentCaptor acQuery;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        summaryRepository.setJdbcTemplate(jdbcTemplate);
    }

    @Test
    public void findPageableRecordsTest() {

        when(jdbcTemplate.query(anyString(), (BeanPropertyRowMapper<SummaryResponse>) any())).thenReturn(new ArrayList<>());

        String sqlQuery = "select * from charge_detail cd, charge_transaction_trace ctt where cd.summary_trace_id = ctt.billing_summary_trace_id and cd.invoice_date <= to_date('2019-12-1', 'MM-DD-YYYY') order by invoice_date desc offset nvl(1-1,1)*1 rows fetch next 1 rows only";
        summaryRepository.findPageableRecords(sqlQuery);

        verify(jdbcTemplate, atLeastOnce()).query((String) acQuery.capture(), (BeanPropertyRowMapper<SummaryResponse>) any());
        Assert.assertThat(acQuery.getValue(), equalTo(sqlQuery));
    }

    @Test
    public void findAllInvoiceDateTest() {
        String sqlQuery = "select distinct to_char(invoice_date, 'MM/DD/YYYY') from charge_detail where invoice_date between add_months(trunc(sysdate, 'month'), -12) and trunc(sysdate, 'month') order by to_char(invoice_date, 'MM/DD/YYYY') desc";
        String activityICA="18373";
        when(jdbcTemplate.queryForList(anyString(), (Class<Object>) any())).thenReturn(new ArrayList<>());

        summaryRepository.findAllInvoiceDate(activityICA);

        verify(jdbcTemplate, atLeastOnce()).queryForList((String) acQuery.capture(), (Class<Object>) any());
        Assert.assertThat(acQuery.getValue(), equalTo(sqlQuery));
    }

    @Test
    public void findAllActivityICATest() {

        when(jdbcTemplate.queryForList(anyString(), (Class<Object>) any())).thenReturn(new ArrayList<>());

        summaryRepository.findAllActivityICA();

        verify(jdbcTemplate, atLeastOnce()).queryForList((String) acQuery.capture(), (Class<Object>) any());
        Assert.assertThat(acQuery.getValue(), equalTo("select distinct activity_ica from charge_detail order by activity_ica desc"));
    }

    @Test
    public void findAllFeederTypeTest() {

        when(jdbcTemplate.queryForList(anyString(), (Class<Object>) any())).thenReturn(new ArrayList<>());

        summaryRepository.findAllFeederType();

        verify(jdbcTemplate, atLeastOnce()).queryForList((String) acQuery.capture(), (Class<Object>) any());
        Assert.assertThat(acQuery.getValue(), equalTo("select distinct feeder_id from charge_transaction_trace order by feeder_id desc"));
    }
}
