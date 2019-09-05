package com.mastercard.billingsearch.controller;

import com.mastercard.billingsearch.entity.SummaryResponse;
import com.mastercard.billingsearch.model.SummaryModel;
import com.mastercard.billingsearch.service.SummaryService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/billing/summary")
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @PostMapping
    public List<SummaryResponse> billingSummary(@Valid @RequestBody SummaryModel summaryModel) {
        return summaryService.getSummaryData(summaryModel);
    }
}
