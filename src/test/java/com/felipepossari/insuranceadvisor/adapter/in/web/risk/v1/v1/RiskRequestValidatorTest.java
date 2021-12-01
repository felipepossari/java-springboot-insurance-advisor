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
    @MethodSource(value = "com.felipepossari.insuranceadvisor.base.request.InvalidCustomerRequestTestBuilder#invalidCustomerRequests")
    void validateShouldErrorsIfRequestIsInValid(String testName, CustomerDataApiRequest request,
                                                RiskApiErrorReason errorReason) {
        var errors = new BeanPropertyBindingResult(request, "");

        riskRequestValidator.validate(request, errors);

        assertTrue(errors.hasErrors());
        assertEquals(errorReason.getCode(), errors.getAllErrors().get(0).getCode());
    }
}
