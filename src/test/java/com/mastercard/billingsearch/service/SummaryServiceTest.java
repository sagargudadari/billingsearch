package com.mastercard.billingsearch.service;

import com.mastercard.billingsearch.entity.SummaryResponse;
import com.mastercard.billingsearch.exception.ResourceNotFoundException;
import com.mastercard.billingsearch.model.SummaryModel;
import com.mastercard.billingsearch.repository.SummaryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SummaryServiceTest {

    @InjectMocks
    SummaryService summaryService;

    @Mock
    SummaryRepository summaryRepository;

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);
        summaryService.setTotal(1);
        summaryService.setSummaryRepository(summaryRepository);

    }

    @Test
    public void getSummaryDataTest() throws ResourceNotFoundException {

        SummaryModel summaryModel = SummaryModel.builder().invoiceDate("2019-12-1").page(1).build();

        when(summaryRepository.findPageableRecords(anyString())).thenReturn(getDummySummaryResponse());

        List<SummaryResponse> summaryResponses = summaryService.getSummaryData(summaryModel);

        assertEquals(1, summaryResponses.size());
        verify(summaryRepository, atLeastOnce()).findPageableRecords(anyString());
    }

    @Test
    public void getSummaryDataWithAndConditionTest() throws ResourceNotFoundException {

        SummaryModel summaryModel = SummaryModel.builder()
                .invoiceDate("2019-12-1")
                .page(1)
                .activityICA("abc")
                .feederType("Auth")
                .invoiceNumber("123")
                .billingEvent("shopping")
                .build();

        when(summaryRepository.findPageableRecords(anyString())).thenReturn(getDummySummaryResponse());

        List<SummaryResponse> summaryResponses = summaryService.getSummaryData(summaryModel);

        assertEquals(1, summaryResponses.size());
        verify(summaryRepository, atLeastOnce()).findPageableRecords(anyString());
    }

    private List<SummaryResponse> getDummySummaryResponse() {
        List<SummaryResponse> summaryResponses = new ArrayList<>();
        SummaryResponse summaryResponse = new SummaryResponse();

        summaryResponse.setSummaryTraceId("123");
        summaryResponse.setInvoiceDate("2019-12-1");
        summaryResponse.setActivityIca("abc");

        summaryResponses.add(summaryResponse);

        return summaryResponses;
    }
}
