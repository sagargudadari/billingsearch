package com.mastercard.billingsearch.repository;


import com.mastercard.billingsearch.entity.SummaryResponse;
import com.mastercard.billingsearch.model.CSVRequest;
import com.mastercard.billingsearch.model.CSVResponse;
import com.mastercard.billingsearch.model.SearchFields;
import com.mastercard.billingsearch.model.UserRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    public List<String> findAllInvoiceDate(String activityICA) {
        try {
			String sqlQuery = "SELECT DISTINCT to_char(chrg_dt, 'MM/DD/YYYY') from charge_detail \r\n" + 
					"WHERE chrg_dt between add_months(trunc(sysdate, 'month'), -12) \r\n" + 
					"and trunc(sysdate, 'month') AND activity_ica=?\r\n" + 
					" order by to_char(chrg_dt, 'MM/DD/YYYY') desc";
			return jdbcTemplate.queryForList(sqlQuery,new Object[] {activityICA}, String.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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

    /**
     * this method is to fetch the summary records
     *
     * @return summary records
     **/
    @Transactional(readOnly=true)
    public List<CSVResponse> getSummaryRecords(int count, CSVRequest csvRequest) {
        String sqlQuery = "SELECT * FROM charge_detail cd , charge_transaction_trace ct WHERE cd.summary_trace_id=ct.billing_summary_trace_id and\r\n"
                + " cd.invoice_Date = ? AND cd.inv_num = ? AND cd.activity_ica =? AND ct.bill_event_id = ?";
        jdbcTemplate.setMaxRows(count);
        return jdbcTemplate.query(sqlQuery,
                new Object[] { csvRequest.getInvoiceDate(), csvRequest.getInvoiceNumber(), csvRequest.getActivityICA(),
                        csvRequest.getBillEventId() },
                new BeanPropertyRowMapper<CSVResponse>(CSVResponse.class));
    }

	public List<SearchFields> getSearchFields(String invoiceDate) {
		List<SearchFields> query = null;
		String sqlQuery = "SELECT activity_ica ,event_src_nam,bill_event_id,inv_num FROM charge_detail WHERE to_char(chrg_dt, 'MM/DD/YYYY')=?";
		query = jdbcTemplate.query(sqlQuery,new Object[] {invoiceDate},new BeanPropertyRowMapper<>(SearchFields.class));
	 return query;
	}
}
