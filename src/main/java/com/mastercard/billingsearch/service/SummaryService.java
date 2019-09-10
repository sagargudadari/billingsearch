package com.mastercard.billingsearch.service;

import com.mastercard.billingsearch.entity.SummaryResponse;
import com.mastercard.billingsearch.exception.ResourceNotFoundException;
import com.mastercard.billingsearch.model.SummaryModel;
import com.mastercard.billingsearch.repository.SummaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static com.mastercard.billingsearch.constant.Constant.RECORD_NOT_FOUND;
import static com.mastercard.billingsearch.constant.Constant.RECORD_NOT_FOUND_MSG;

@Slf4j
@Service
public class SummaryService {

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

    public List<String> getInvoiceDates() {
        log.info("Get the list of InvoiceDate");
        return summaryRepository.findAllInvoiceDate();
    }

    public List<String> getActivityIcas() {
        log.info("Get the list of ActivityICA");
        return summaryRepository.findAllActivityICA();
    }

    public List<String> getFeederTypes() {
        log.info("Get the list of FeederType");
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

    void setTotal(int total) {
        this.total = total;
    }

    void setSummaryRepository(SummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }
}
