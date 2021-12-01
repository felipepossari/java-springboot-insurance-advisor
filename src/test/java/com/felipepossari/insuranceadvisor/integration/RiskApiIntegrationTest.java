package com.felipepossari.insuranceadvisor.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.model.ErrorResponse;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerDataApiRequest;
import com.felipepossari.insuranceadvisor.base.request.CustomerDataApiRequestTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.felipepossari.insuranceadvisor.adapter.AdapterConstants.ENDPOINT_RISKS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RiskApiIntegrationTest {

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void postRiskShouldWorkIfRequestIsValid() throws Exception {
        CustomerDataApiRequest request = CustomerDataApiRequestTestBuilder.aCustomerDataRequest().build();

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_RISKS)
                                .contentType(APPLICATION_JSON)
                                .content(om.writeValueAsString(request))
                                .header(CONTENT_TYPE, APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource(value = "com.felipepossari.insuranceadvisor.base.request.InvalidCustomerRequestTestBuilder#invalidCustomerRequests")
    void postRiskShouldReturnBadRequestIfRequestIsValid(String testName, CustomerDataApiRequest request,
                                                        RiskApiErrorReason errorReason) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_RISKS)
                                .contentType(APPLICATION_JSON)
                                .content(om.writeValueAsString(request))
                                .header(CONTENT_TYPE, APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        List<ErrorResponse> errors = parserErrorsResponses(mvcResult.getResponse().getContentAsString());

        assertFalse(errors.isEmpty());
        assertEquals(errorReason.getCode(), errors.get(0).getCode());
    }

    private List<ErrorResponse> parserErrorsResponses(String contentAsString) throws JsonProcessingException {
        return om.readValue(contentAsString, new TypeReference<>() {
        });
    }
}
