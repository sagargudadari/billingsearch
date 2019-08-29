package com.mastercard.billingsearch.controller;

import com.mastercard.billingsearch.model.SummaryRequestParamsModel;
import com.mastercard.billingsearch.service.SummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/billing")
public class BillingSearchController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SummaryService summaryService;

    @GetMapping("/summary")
    public ResponseEntity<Object> getBillingSummary(@Valid SummaryRequestParamsModel requestParamModel){

        logger.info("Incoming request for summary with {}", requestParamModel);

        List<Object> responseBody = summaryService.getSummaryData(requestParamModel);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
