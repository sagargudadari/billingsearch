package com.mastercard.billingsearch.controller;

import com.mastercard.billingsearch.entity.ErrorDetails;
import com.mastercard.billingsearch.entity.SummaryResponse;
import com.mastercard.billingsearch.exception.ResourceNotFoundException;
import com.mastercard.billingsearch.model.SummaryModel;
import com.mastercard.billingsearch.service.SummaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/billing/search/summary")
@Api("Billing Search API endpoints for retrieving, downloading invoice and transaction details from system.")
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @PostMapping(produces = "application/json")
    @ApiOperation("Returns list of Summary/Invoice details from the system.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Record Not Found", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetails.class )
    })
    public ResponseEntity<List<SummaryResponse>> billingSummary(
            @Valid @RequestBody SummaryModel summaryModel) throws ResourceNotFoundException {
        return new ResponseEntity<>(summaryService.getSummaryData(summaryModel), HttpStatus.OK);
    }
}
