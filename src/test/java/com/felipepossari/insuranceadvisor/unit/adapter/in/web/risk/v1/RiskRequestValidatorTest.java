package com.felipepossari.insuranceadvisor.unit.adapter.in.web.risk.v1;

import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.RiskRequestValidator;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerDataApiRequest;
import com.felipepossari.insuranceadvisor.base.request.CustomerDataApiRequestTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;

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
    @MethodSource(value = "com.felipepossari.insuranceadvisor.base.request.InvalidCustomerRequestArguments#invalidCustomerRequests")
    void validateShouldErrorsIfRequestIsInValid(String testName, CustomerDataApiRequest request,
                                                RiskApiErrorReason errorReason) {
        var errors = new BeanPropertyBindingResult(request, "");

        riskRequestValidator.validate(request, errors);

        assertTrue(errors.hasErrors());
        assertEquals(errorReason.getCode(), errors.getAllErrors().get(0).getCode());
    }
}
