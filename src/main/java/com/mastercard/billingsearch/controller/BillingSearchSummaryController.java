package com.mastercard.billingsearch.controller;

import com.mastercard.billingsearch.model.SummaryModel;
import com.mastercard.billingsearch.service.SummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/billing/summary")
public class BillingSearchSummaryController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SummaryService summaryService;

    @PostMapping
    public List<Map<String, Object>> billingSummary(@Valid @RequestBody SummaryModel summaryModel) {
        return summaryService.getSummaryData();
    }
}
