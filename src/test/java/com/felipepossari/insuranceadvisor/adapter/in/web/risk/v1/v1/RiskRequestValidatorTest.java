package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.v1;

import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.RiskRequestValidator;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerDataApiRequest;
import com.felipepossari.insuranceadvisor.base.request.CustomerDataApiRequestTestBuilder;
import com.felipepossari.insuranceadvisor.base.request.CustomerHouseApiRequestTestBuilder;
import com.felipepossari.insuranceadvisor.base.request.CustomerVehicleApiRequestTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_AGE_INVALID;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_DEPENDENTS_INVALID;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_HOUSE_EMPTY;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_HOUSE_INVALID;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_INCOME_INVALID;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_MARITAL_STATUS_EMPTY;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_MARITAL_STATUS_INVALID;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_RISK_QUESTIONS_EMPTY;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_RISK_QUESTIONS_INVALID_LENGTH;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_RISK_QUESTIONS_INVALID_VALUE;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_VEHICLE_EMPTY;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_VEHICLE_INVALID;
import static com.felipepossari.insuranceadvisor.base.DefaultConstants.MARITAL_STATUS_INVALID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class RiskRequestValidatorTest {

    @InjectMocks
    RiskRequestValidator riskRequestValidator;

    @Test
    void validateShouldReturnNoErrorsIfRequestIsValid() {
        CustomerDataApiRequest request = CustomerDataApiRequestTestBuilder.aCustomerDataRequest().build();
        var errors = new BeanPropertyBindingResult(request, "");

        riskRequestValidator.validate(request, errors);

        assertFalse(errors.hasErrors());
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource(value = "invalidCustomerRequests")
    void validateShouldErrorsIfRequestIsInValid(String testName, CustomerDataApiRequest request,
                                                RiskApiErrorReason errorReason) {
        var errors = new BeanPropertyBindingResult(request, "");

        riskRequestValidator.validate(request, errors);

        assertTrue(errors.hasErrors());
        assertEquals(errorReason.getCode(), errors.getAllErrors().get(0).getCode());
    }

    private static Stream<Arguments> invalidCustomerRequests() {
        return Stream.of(
                Arguments.arguments("invalidAgeRequest",invalidAgeRequest(), FIELD_AGE_INVALID),
                Arguments.arguments("invalidNullAgeRequest", invalidNullAgeRequest(), FIELD_AGE_INVALID),
                Arguments.arguments("invalidDependentsRequest", invalidDependentsRequest(), FIELD_DEPENDENTS_INVALID),
                Arguments.arguments("invalidNullObjectHouseRequest", invalidNullObjectHouseRequest(), FIELD_HOUSE_EMPTY),
                Arguments.arguments("invalidEmptyHouseRequest", invalidEmptyHouseRequest(), FIELD_HOUSE_EMPTY),
                Arguments.arguments("invalidDifferentStatusHouseRequest", invalidDifferentStatusHouseRequest(), FIELD_HOUSE_INVALID),
                Arguments.arguments("invalidIncomeRequest", invalidIncomeRequest(), FIELD_INCOME_INVALID),
                Arguments.arguments("invalidEmptyMaritalStatusRequest", invalidEmptyMaritalStatusRequest(), FIELD_MARITAL_STATUS_EMPTY),
                Arguments.arguments("invalidNullMaritalStatusRequest", invalidNullMaritalStatusRequest(), FIELD_MARITAL_STATUS_EMPTY),
                Arguments.arguments("invalidDifferentMaritalStatusRequest", invalidDifferentMaritalStatusRequest(), FIELD_MARITAL_STATUS_INVALID),
                Arguments.arguments("invalidNullObjectRiskQuestionsRequest", invalidNullObjectRiskQuestionsRequest(), FIELD_RISK_QUESTIONS_EMPTY),
                Arguments.arguments("invalidLengthRiskQuestionsRequest", invalidLengthRiskQuestionsRequest(), FIELD_RISK_QUESTIONS_INVALID_LENGTH),
                Arguments.arguments("invalidAnswerValueRiskQuestionsRequest", invalidAnswerValueRiskQuestionsRequest(), FIELD_RISK_QUESTIONS_INVALID_VALUE),
                Arguments.arguments("invalidNullObjectVehicleRequest", invalidNullObjectVehicleRequest(), FIELD_VEHICLE_EMPTY),
                Arguments.arguments("invalidNullYearVehicleRequest", invalidNullYearVehicleRequest(), FIELD_VEHICLE_INVALID),
                Arguments.arguments("invalidYearVehicleRequest", invalidYearVehicleRequest(), FIELD_VEHICLE_INVALID),
                Arguments.arguments("invalidYearBiggerCurrentYearPlusTwoVehicleRequest", invalidYearBiggerCurrentYearPlusTwoVehicleRequest(), FIELD_VEHICLE_INVALID)
        );
    }

    private static CustomerDataApiRequest invalidAgeRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest().age(-1).build();
    }

    private static CustomerDataApiRequest invalidNullAgeRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest().age(null).build();
    }

    private static CustomerDataApiRequest invalidDependentsRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest().dependants(null).build();
    }

    private static CustomerDataApiRequest invalidNullObjectHouseRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest().house(null).build();
    }

    private static CustomerDataApiRequest invalidEmptyHouseRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest()
                .house(CustomerHouseApiRequestTestBuilder.anEmptyHouseStatusRequest().build())
                .build();
    }

    private static CustomerDataApiRequest invalidDifferentStatusHouseRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest()
                .house(CustomerHouseApiRequestTestBuilder.anInvalidHouseStatusRequest().build())
                .build();
    }

    private static CustomerDataApiRequest invalidIncomeRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest().income(-1).build();
    }

    private static CustomerDataApiRequest invalidEmptyMaritalStatusRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest().maritalStatus("").build();
    }

    private static CustomerDataApiRequest invalidNullMaritalStatusRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest().maritalStatus(null).build();
    }

    private static CustomerDataApiRequest invalidDifferentMaritalStatusRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest()
                .maritalStatus(MARITAL_STATUS_INVALID).build();
    }

    private static CustomerDataApiRequest invalidNullObjectRiskQuestionsRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest().riskQuestions(null).build();
    }

    private static CustomerDataApiRequest invalidLengthRiskQuestionsRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest().riskQuestions(new Integer[]{0, 1}).build();
    }

    private static CustomerDataApiRequest invalidAnswerValueRiskQuestionsRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest().riskQuestions(new Integer[]{0, 1, 2}).build();
    }

    private static CustomerDataApiRequest invalidNullObjectVehicleRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest().vehicle(null).build();
    }

    private static CustomerDataApiRequest invalidNullYearVehicleRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest()
                .vehicle(CustomerVehicleApiRequestTestBuilder.aNullVehicleYearRequest().build()).build();
    }

    private static CustomerDataApiRequest invalidYearVehicleRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest()
                .vehicle(CustomerVehicleApiRequestTestBuilder.anInvalidVehicleYearRequest().build()).build();
    }

    private static CustomerDataApiRequest invalidYearBiggerCurrentYearPlusTwoVehicleRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest()
                .vehicle(
                        CustomerVehicleApiRequestTestBuilder
                                .aVehicleRequest()
                                .year(LocalDateTime.now().getYear() + 2)
                                .build())
                .build();
    }
}
