package com.mastercard.billingsearch.service;

import com.mastercard.billingsearch.repository.SummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SummaryService {

    @Autowired
    private SummaryRepository summaryRepository;

    public List<Map<String, Object>> getSummaryData() {
        return summaryRepository.findByAll();
    }
}
