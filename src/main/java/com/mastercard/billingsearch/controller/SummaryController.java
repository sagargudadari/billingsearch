package com.mastercard.billingsearch.controller;

import com.mastercard.billingsearch.exception.ResourceNotFoundException;
import com.mastercard.billingsearch.model.SummaryModel;
import com.mastercard.billingsearch.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/billing/summary")
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @PostMapping
    public ResponseEntity<Object> billingSummary(
            @Valid @RequestBody SummaryModel summaryModel) throws ResourceNotFoundException {
        return new ResponseEntity<>(summaryService.getSummaryData(summaryModel), HttpStatus.OK);
    }
}
