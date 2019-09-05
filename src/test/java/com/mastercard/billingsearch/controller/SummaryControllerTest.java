package com.mastercard.billingsearch.controller;

import com.mastercard.billingsearch.Application;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SummaryControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void verifyBillingSummarySuccessCase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/billing/summary")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"invoiceDate\" : \"2020-12-1\", \"page\" : \"1\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].summaryTraceId").exists())
                .andExpect(jsonPath("$.[0].invoiceDate").value("2019-04-01"))
                .andExpect(jsonPath("$.[0].invoiceIca").exists())
                .andExpect(jsonPath("$.[0].activityIca").exists())
                .andExpect(jsonPath("$.[0].feederId").exists())
                .andDo(print());
    }

    @Test
    public void verifyBillingSummaryFailureCase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/billing/summary")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"page\" : \"1\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.invoiceDate").value("must not be null"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());
    }

}
