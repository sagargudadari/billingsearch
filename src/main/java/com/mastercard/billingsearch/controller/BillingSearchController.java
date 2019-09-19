package com.mastercard.billingsearch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.billingsearch.model.SearchFields;
import com.mastercard.billingsearch.service.SummaryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/billing/search")
@Api("Billing Search API endpoints for retrieving invoiceDate, activityIca, feederType details from system.")
public class BillingSearchController {

    @Autowired
    private SummaryService summaryService;

    @GetMapping(value = "invoice-dates", produces = "application/json")
    @ApiOperation("Returns list of Invoice date details from the system.")
    public ResponseEntity<List<String>> invoiceDate(@RequestHeader("correlationId") String correlationId,@RequestParam("invoiceICA")String invoiceICA) {
        return new ResponseEntity<>(summaryService.getInvoiceDates(), HttpStatus.OK);
    }

    @GetMapping(value = "search-fields", produces = "application/json")
    @ApiOperation("Returns list of search fields from the system.")
    public ResponseEntity<SearchFields> searchFields(@RequestHeader("correlationId") String correlationId,@RequestParam("invoiceICA")String invoiceICA,@RequestParam("invoiceDate")String invoiceDate) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
