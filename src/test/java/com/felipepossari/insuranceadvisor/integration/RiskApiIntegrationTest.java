package com.felipepossari.insuranceadvisor.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.model.ErrorResponse;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerDataApiRequest;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.response.RiskProfileApiResponse;
import com.felipepossari.insuranceadvisor.application.domain.customer.MaritalStatus;
import com.felipepossari.insuranceadvisor.base.request.CustomerDataApiRequestTestBuilder;
import com.felipepossari.insuranceadvisor.base.request.CustomerHouseApiRequestTestBuilder;
import com.felipepossari.insuranceadvisor.base.request.CustomerVehicleApiRequestTestBuilder;
import com.felipepossari.insuranceadvisor.base.response.RiskProfileApiResponseTestBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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
import java.util.stream.Stream;

import static com.felipepossari.insuranceadvisor.adapter.AdapterConstants.ENDPOINT_RISKS;
import static com.felipepossari.insuranceadvisor.application.domain.ScoreResult.ECONOMIC;
import static com.felipepossari.insuranceadvisor.application.domain.ScoreResult.INELIGIBLE;
import static com.felipepossari.insuranceadvisor.application.domain.ScoreResult.REGULAR;
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

    @ParameterizedTest
    @MethodSource(value = "validCustomerRequestResponse")
    void postRiskShouldWorkIfRequestIsValid(CustomerDataApiRequest request,
                                            RiskProfileApiResponse expectedResponse) throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_RISKS)
                                .contentType(APPLICATION_JSON)
                                .content(om.writeValueAsString(request))
                                .header(CONTENT_TYPE, APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        RiskProfileApiResponse currentResponse = parseResponse(mvcResult.getResponse().getContentAsString());
        assertEquals(expectedResponse, currentResponse);
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource(value = "com.felipepossari.insuranceadvisor.base.request.InvalidCustomerRequestArguments#invalidCustomerRequests")
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

    private RiskProfileApiResponse parseResponse(String contentAsString) throws JsonProcessingException {
        return om.readValue(contentAsString, RiskProfileApiResponse.class);
    }

    private List<ErrorResponse> parserErrorsResponses(String contentAsString) throws JsonProcessingException {
        return om.readValue(contentAsString, new TypeReference<>() {
        });
    }

    private static Stream<Arguments> validCustomerRequestResponse() {
        return Stream.of(
                Arguments.arguments(
                        CustomerDataApiRequestTestBuilder
                                .aCustomerDataRequest()
                                .age(35)
                                .dependants(2)
                                .house(CustomerHouseApiRequestTestBuilder.anOwnedHouseRequest().build())
                                .income(0)
                                .maritalStatus(MaritalStatus.MARRIED.name())
                                .riskQuestions(new Integer[]{0, 1, 0})
                                .vehicle(CustomerVehicleApiRequestTestBuilder.aVehicleRequest().year(2018).build())
                                .build()
                        ,
                        RiskProfileApiResponseTestBuilder.aRiskProfileApiResponse()
                                .auto(REGULAR)
                                .disability(INELIGIBLE)
                                .home(ECONOMIC)
                                .life(REGULAR)
                                .build()
                )
        );
    }
}
