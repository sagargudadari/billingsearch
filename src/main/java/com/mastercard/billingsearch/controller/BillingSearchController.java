package com.mastercard.billingsearch.controller;

import com.mastercard.billingsearch.service.SummaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/billing/search")
@Api("Billing Search API endpoints for retrieving invoiceDate, activityIca, feederType details from system.")
public class BillingSearchController {

    @Autowired
    private SummaryService summaryService;

    @GetMapping(value = "invoice-dates", produces = "application/json")
    @ApiOperation("Returns only list of Invoice date details from the system.")
    public ResponseEntity<List<String>> invoiceDate() {
        return new ResponseEntity<>(summaryService.getInvoiceDates(), HttpStatus.OK);
    }

    @GetMapping(value = "activity-icas", produces = "application/json")
    @ApiOperation("Returns only list of activityICA details from the system.")
    public ResponseEntity<List<String>> activityICA() {
        return new ResponseEntity<>(summaryService.getActivityIcas(), HttpStatus.OK);
    }

    @GetMapping(value = "feeder-systems", produces = "application/json")
    @ApiOperation("Returns only list of feeder-systems details from the system.")
    public ResponseEntity<List<String>> feederType() {
        return new ResponseEntity<>(summaryService.getFeederTypes(), HttpStatus.OK);
    }
}
