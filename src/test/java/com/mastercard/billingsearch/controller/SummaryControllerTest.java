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

import static com.mastercard.billingsearch.constant.Constant.*;
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
    public void verifyBillingSummarySuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/billing/summary")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"invoiceDate\":\"2020-12-1\",\n" +
                        "\t\"page\":\"1\",\n" +
                        "\t\"billingEvent\":\"dasdas\",\n" +
                        "\t\"feederType\" : \"Ravi\"\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].summaryTraceId").exists())
                .andExpect(jsonPath("$.[0].invoiceDate").exists())
                .andExpect(jsonPath("$.[0].invoiceIca").exists())
                .andExpect(jsonPath("$.[0].activityIca").exists())
                .andExpect(jsonPath("$.[0].feederId").exists())
                .andDo(print());
    }

    @Test
    public void verifyBillingSummaryWithMandatoryAttributeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/billing/summary")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"invoiceDate\" : \"2020-12-1\", \"page\" : \"1\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].summaryTraceId").exists())
                .andExpect(jsonPath("$.[0].invoiceDate").exists())
                .andExpect(jsonPath("$.[0].invoiceIca").exists())
                .andExpect(jsonPath("$.[0].activityIca").exists())
                .andExpect(jsonPath("$.[0].feederId").exists())
                .andDo(print());
    }

    @Test
    public void verifyBillingSummaryValidationFailureTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/billing/summary")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"billingEvent\":\"dasdas\",\n" +
                        "\t\"feederType\" : \"Ravi\"\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(VALIDATION_FAILED))
                .andExpect(jsonPath("$.errors.invoiceDate").value("must not be null"))
                .andExpect(jsonPath("$.errors.page").value("must be greater than or equal to 1"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());
    }

    @Test
    public void verifyBillingSummaryRecordNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/billing/summary")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"invoiceDate\":\"2020-12-1\",\n" +
                        "\t\"page\":\"10\",\n" +
                        "\t\"billingEvent\":\"dasdas\",\n" +
                        "\t\"feederType\" : \"Ravi\"\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(RECORD_NOT_FOUND))
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());
    }

    @Test
    public void verifyBillingSummaryInternalServerTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/billing/summary")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"invoiceDate\":\"20-12-1\",\n" +
                        "\t\"page\":\"10\",\n" +
                        "\t\"billingEvent\":\"dasdas\",\n" +
                        "\t\"feederType\" : \"Ravi\"\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(SERVER_ERROR))
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());
    }

}
