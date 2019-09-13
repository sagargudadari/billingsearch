package com.mastercard.billingsearch.service;

import com.mastercard.billingsearch.entity.SummaryResponse;
import com.mastercard.billingsearch.exception.ResourceNotFoundException;
import com.mastercard.billingsearch.model.SummaryModel;
import com.mastercard.billingsearch.repository.SummaryRepository;
import com.mastercard.billingsearch.service.impl.SummaryServiceImpl;
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
    SummaryServiceImpl summaryService;

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

    @Test
    public void getInvoiceDatesTest() {

        when(summaryRepository.findAllInvoiceDate()).thenReturn(getDummyInvoiceDateResponse());

        List<String> summaryResponses = summaryService.getInvoiceDates();

        assertEquals(2, summaryResponses.size());
        verify(summaryRepository, atLeastOnce()).findAllInvoiceDate();
    }

    @Test
    public void getActivityIcasTest() {

        when(summaryRepository.findAllActivityICA()).thenReturn(getDummyActivityIcaResponse());

        List<String> summaryResponses = summaryService.getActivityIcas();

        assertEquals(1, summaryResponses.size());
        verify(summaryRepository, atLeastOnce()).findAllActivityICA();
    }

    @Test
    public void getFeederTypesTest() {

        when(summaryRepository.findAllFeederType()).thenReturn(getDummyFeederTypeResponse());

        List<String> summaryResponses = summaryService.getFeederTypes();

        assertEquals(1, summaryResponses.size());
        verify(summaryRepository, atLeastOnce()).findAllFeederType();
    }

    private List<String> getDummyFeederTypeResponse() {
        List<String> activityIca = new ArrayList<>();
        activityIca.add("AUTH");
        return activityIca;
    }

    private List<String> getDummyActivityIcaResponse() {
        List<String> activityIca = new ArrayList<>();
        activityIca.add("ABC");
        return activityIca;
    }

    private List<String> getDummyInvoiceDateResponse() {
        List<String> invoiceDates = new ArrayList<>();
        invoiceDates.add("1/1/2019");
        invoiceDates.add("2/1/2019");
        return invoiceDates;
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
