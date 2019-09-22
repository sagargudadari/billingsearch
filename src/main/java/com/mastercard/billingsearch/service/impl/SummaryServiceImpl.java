package com.mastercard.billingsearch.service.impl;

import com.mastercard.billingsearch.entity.SummaryResponse;
import com.mastercard.billingsearch.exception.ResourceNotFoundException;
import com.mastercard.billingsearch.model.CSVRequest;
import com.mastercard.billingsearch.model.CSVResponse;
import com.mastercard.billingsearch.model.SearchFields;
import com.mastercard.billingsearch.model.SummaryModel;
import com.mastercard.billingsearch.repository.SummaryRepository;
import com.mastercard.billingsearch.service.SummaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static com.mastercard.billingsearch.constant.Constant.RECORD_NOT_FOUND;
import static com.mastercard.billingsearch.constant.Constant.RECORD_NOT_FOUND_MSG;

@Slf4j
@Service
public class SummaryServiceImpl implements SummaryService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private SummaryRepository summaryRepository;

	@Value("${page.per.records}")
	private Integer total;

	public List<SummaryResponse> getSummaryData(SummaryModel summaryModel) throws ResourceNotFoundException {

		Optional<List<SummaryResponse>> summaryResponses = Optional.ofNullable(
				summaryRepository.findPageableRecords(buildPageableQueryString(summaryModel)));

		if(summaryResponses.get().isEmpty()) {
			log.info("{} : {}/query above.", RECORD_NOT_FOUND, RECORD_NOT_FOUND_MSG);
			throw new ResourceNotFoundException(RECORD_NOT_FOUND_MSG + summaryModel);
		}
		return summaryResponses.get();
	}

	public List<String> getInvoiceDates(String activityICA) {
		log.info("DB call to get all InvoiceDates");
		return summaryRepository.findAllInvoiceDate(activityICA);
	}

	public List<String> getActivityIcas() {
		log.info("DB call to get all ActivityICA");
		return summaryRepository.findAllActivityICA();
	}

	public List<String> getFeederTypes() {
		log.info("DB call to get all FeederTypes");
		return summaryRepository.findAllFeederType();
	}

	private String buildPageableQueryString(SummaryModel summaryModel) {

		log.info("BuildPageableQueryString with data {} and total record per page want '{}'", summaryModel, total);

		String selectClause = "select * from charge_detail cd, charge_transaction_trace ctt"
				+ " where cd.summary_trace_id = ctt.billing_summary_trace_id"
				+ " and cd.invoice_date <= to_date('" + summaryModel.getInvoiceDate() + "', 'MM-DD-YYYY')";

		String whereClause = buildAndClauseString(summaryModel);

		String orderClause = " order by invoice_date desc"
				+ " offset nvl(" + summaryModel.getPage() + "-1,1)*" + total
				+ " rows fetch next " + total + " rows only";

		log.info("DB call with pageable query string '{}{}{}'", selectClause, whereClause, orderClause);

		return selectClause.concat(whereClause).concat(orderClause);
	}

	private String buildAndClauseString(SummaryModel summaryModel) {
		String andClause = "";

		Predicate<String> checkNull = Objects::nonNull;

		if (checkNull.test(summaryModel.getBillingEvent()))
			andClause += " and ctt.billing_event_id ='" + summaryModel.getBillingEvent() + "'";
		if (checkNull.test(summaryModel.getActivityICA()))
			andClause += " and cd.activity_ica ='" + summaryModel.getActivityICA() + "'";
		if (checkNull.test(summaryModel.getInvoiceNumber()))
			andClause += " and cd.inv_num ='" + summaryModel.getInvoiceNumber() + "'";
		if (checkNull.test(summaryModel.getFeederType()))
			andClause += " and ctt.feeder_id ='" + summaryModel.getFeederType() + "'";

		return andClause;
	}

	@Override
	public List<CSVResponse> exportSummaryRecords(String userId, CSVRequest csvRequest) {
		return summaryRepository.getSummaryRecords(Integer.parseInt(getDownloadSummaryCount(userId)), csvRequest);
	}
	public String getDownloadSummaryCount(String userId){
		String sqlQueryToFetchRoleName="SELECT download_summary_count FROM role_mapping where ROLE_NAME IN (SELECT ROLE_NAME FROM user_roles where user_id=?)";
		 return jdbcTemplate.queryForObject(
	                sqlQueryToFetchRoleName,
	                new Object[]{userId},
	                String.class
	        );
		
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setSummaryRepository(SummaryRepository summaryRepository) {
		this.summaryRepository = summaryRepository;
	}

	@Override
	public List<SearchFields> getSearchFields(String invoiceDate) {
		
		return summaryRepository.getSearchFields(invoiceDate);
	}
}