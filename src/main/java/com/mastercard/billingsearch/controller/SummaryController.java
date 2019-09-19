package com.mastercard.billingsearch.controller;

import com.mastercard.billingsearch.config.RootConfiguration;
import com.mastercard.billingsearch.entity.ErrorDetails;
import com.mastercard.billingsearch.entity.SummaryResponse;
import com.mastercard.billingsearch.exception.ResourceNotFoundException;
import com.mastercard.billingsearch.model.CSVRequest;
import com.mastercard.billingsearch.model.CSVResponse;
import com.mastercard.billingsearch.model.SummaryModel;
import com.mastercard.billingsearch.service.SummaryService;
import com.mastercard.billingsearch.utility.ExportCSV;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/billing/search/summary")
@Api("Billing Search API endpoints for retrieving, downloading invoice and transaction details from system.")
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @Autowired
    private RootConfiguration rootConfiguration;

    @PostMapping(produces = "application/json")
    @ApiOperation("Returns list of Summary/Invoice details from the system.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Record Not Found", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetails.class )
    })
    public ResponseEntity<List<SummaryResponse>> billingSummary(
            @Valid @RequestBody SummaryModel summaryModel,@RequestHeader("correlationId") String correlationId,@RequestHeader("userId") String userId,
            @RequestHeader("roleName") String roleName) throws ResourceNotFoundException {
        return new ResponseEntity<>(summaryService.getSummaryData(summaryModel), HttpStatus.OK);
    }

    /***
     * @param csvRequest
     * @return csv file
     */

    @PostMapping(value = "/download")
    @ApiOperation("Returns csv file contains list of Summary/Invoice details from the system.")
    public ResponseEntity<Object> billingSummaryDownload(@RequestBody CSVRequest csvRequest,
    													@RequestHeader("correlationId") String correlationId,
                                                         @RequestHeader("userId") String userId,
                                                         @RequestHeader("roleName") String roleName,
                                                         HttpServletResponse response)
            throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        List<CSVResponse> summaryReport = summaryService.exportSummaryRecords(userId, csvRequest);
        ExportCSV.export(response, summaryReport, rootConfiguration.getBillingSummaryFileName());
        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
