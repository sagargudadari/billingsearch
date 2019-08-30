package com.mastercard.billingsearch.service;

import com.mastercard.billingsearch.model.SummaryModel;
import com.mastercard.billingsearch.repository.SummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SummaryService {

    @Autowired
    private SummaryRepository summaryRepository;

    public Page<Object> getSummaryData(SummaryModel summaryModel) {
        int total = 2;
        int pageId = 0;
        if (summaryModel.getPage() != 1) {
            pageId = (summaryModel.getPage() -1)*total+1;
        }

        return summaryRepository.findByAll(pageId, total);
    }
}
