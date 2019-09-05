package com.mastercard.billingsearch.service;

import com.mastercard.billingsearch.entity.SummaryResponse;
import com.mastercard.billingsearch.model.SummaryModel;
import com.mastercard.billingsearch.repository.SummaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Slf4j
@Service
public class SummaryService {

    @Autowired
    private SummaryRepository summaryRepository;

    @Value("${page.per.records}")
    private Integer total;

    public List<SummaryResponse> getSummaryData(SummaryModel summaryModel) {
        return summaryRepository.findPageableRecords(buildPageableQueryString(summaryModel));
    }

    private String buildPageableQueryString(SummaryModel summaryModel) {

        log.info("Here for making query with data {}", summaryModel);

        String selectClause = "select * from charge_detail cd, charge_transaction_trace ctt"
                + " where cd.summary_trace_id = ctt.billing_summary_trace_id"
                + " and cd.invoice_date <= to_date('" + summaryModel.getInvoiceDate() + "', 'yyyy/mm/dd')";

        String whereClause = buildAndClauseString(summaryModel);

        String orderClause = " order by invoice_date desc"
                + " offset nvl(" + summaryModel.getPage() + "-1,1)*" + total
                + " rows fetch next " + total + " rows only";

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

    public void setTotal(int total) {
        this.total = total;
    }

    public void setSummaryRepository(SummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }
}
