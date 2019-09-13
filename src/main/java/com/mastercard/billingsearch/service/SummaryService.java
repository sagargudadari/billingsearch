package com.mastercard.billingsearch.service;

import com.mastercard.billingsearch.entity.SummaryResponse;
import com.mastercard.billingsearch.exception.ResourceNotFoundException;
import com.mastercard.billingsearch.model.CSVRequest;
import com.mastercard.billingsearch.model.CSVResponse;
import com.mastercard.billingsearch.model.SummaryModel;

import java.util.List;

public interface SummaryService {

    List<String> getInvoiceDates();
    List<String> getActivityIcas();
    List<String> getFeederTypes();
    List<SummaryResponse> getSummaryData(SummaryModel summaryModel) throws ResourceNotFoundException;
    List<CSVResponse> exportSummaryRecords(String userId, CSVRequest csvRequest);

}
