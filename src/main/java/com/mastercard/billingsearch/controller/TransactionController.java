package com.mastercard.billingsearch.controller;

import com.mastercard.billingsearch.config.RootConfiguration;
import com.mastercard.billingsearch.service.TransactionService;
import com.mastercard.billingsearch.utility.ExportCSV;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/billing/details")
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;

	@Autowired
	private RootConfiguration rootConfiguration;

	/***
	 * @param imeTraceId
	 * @param feederType		 * 
	 * @return billing Transaction Details
	 */
	@GetMapping(value = "{imeTraceId}")
	@ApiOperation("Returns list of billing transaction details from the system using the dynamic fields configured for the user in the asFields column in the Data Base.There is no fixed response model for this endpoint as it is dynamic based on roles of the user")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Bad request", response = ErrorDetails.class),
			@ApiResponse(code = 404, message = "Record Not Found", response = ErrorDetails.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetails.class )
	})
	public ResponseEntity<Object> billingTransactionDetail(@PathVariable("imeTraceId") String imeTraceId,
														   @RequestParam("feederType") String feederType,
														   @RequestHeader("userId") String userId) {
		return new ResponseEntity<>(transactionService.billingTransactionDetails(feederType, userId,
				rootConfiguration.getTotalRecords()), HttpStatus.OK);
	}
	
	/***
	 * @param imeTraceId
	 * @param feederType		 
	 * @return csv file
	 */
	@GetMapping(value = "{imeTraceId}/download")
	@ApiOperation("Returns csv file contains list of billing transaction details from the system.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Bad request", response = ErrorDetails.class),
			@ApiResponse(code = 404, message = "Record Not Found", response = ErrorDetails.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetails.class )
	})
	public ResponseEntity<String> billingDetailsDownload(@PathVariable("imeTraceId") String imeTraceId,
														 @RequestParam("feederType") String feederType,
														 @RequestHeader("userId") String userId,
														 HttpServletResponse response)
					throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {

		List<Map<String, Object>> billingTransactionDetails = transactionService.billingTransactionDetails(feederType,
				userId, rootConfiguration.getTotalRecords());
		ExportCSV.exportCSV(response, billingTransactionDetails, rootConfiguration.getTransactiondetailfile());
		return ResponseEntity.status(HttpStatus.OK).build();

	}
}
