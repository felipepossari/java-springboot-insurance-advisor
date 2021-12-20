package com.felipepossari.insuranceadvisor.base.request;

import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerDataApiRequest;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_AGE_INVALID;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_DEPENDENTS_INVALID;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_HOUSE_INVALID;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_INCOME_INVALID;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_MARITAL_STATUS_EMPTY;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_MARITAL_STATUS_INVALID;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_RISK_QUESTIONS_EMPTY;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_RISK_QUESTIONS_INVALID_LENGTH;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_RISK_QUESTIONS_INVALID_VALUE;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.FIELD_VEHICLE_INVALID;
import static com.felipepossari.insuranceadvisor.base.DefaultConstants.MARITAL_STATUS_INVALID;

public class InvalidCustomerRequestArguments {
    public static Stream<Arguments> invalidCustomerRequests() {
        return Stream.of(
                Arguments.arguments("invalidAgeRequest", invalidAgeRequest(), FIELD_AGE_INVALID),
                Arguments.arguments("invalidNullAgeRequest", invalidNullAgeRequest(), FIELD_AGE_INVALID),
                Arguments.arguments("invalidDependentsRequest", invalidDependentsRequest(), FIELD_DEPENDENTS_INVALID),
                Arguments.arguments("invalidDifferentStatusHouseRequest", invalidDifferentStatusHouseRequest(), FIELD_HOUSE_INVALID),
                Arguments.arguments("invalidIncomeRequest", invalidIncomeRequest(), FIELD_INCOME_INVALID),
                Arguments.arguments("invalidEmptyMaritalStatusRequest", invalidEmptyMaritalStatusRequest(), FIELD_MARITAL_STATUS_EMPTY),
                Arguments.arguments("invalidNullMaritalStatusRequest", invalidNullMaritalStatusRequest(), FIELD_MARITAL_STATUS_EMPTY),
                Arguments.arguments("invalidDifferentMaritalStatusRequest", invalidDifferentMaritalStatusRequest(), FIELD_MARITAL_STATUS_INVALID),
                Arguments.arguments("invalidNullObjectRiskQuestionsRequest", invalidNullObjectRiskQuestionsRequest(), FIELD_RISK_QUESTIONS_EMPTY),
                Arguments.arguments("invalidLengthRiskQuestionsRequest", invalidLengthRiskQuestionsRequest(), FIELD_RISK_QUESTIONS_INVALID_LENGTH),
                Arguments.arguments("invalidAnswerValueRiskQuestionsRequest", invalidAnswerValueRiskQuestionsRequest(), FIELD_RISK_QUESTIONS_INVALID_VALUE),
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

    private static CustomerDataApiRequest invalidDifferentStatusHouseRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest()
                .houses(
                        List.of(
                                CustomerHouseApiRequestTestBuilder.anOwnedHouseRequest().build(),
                                CustomerHouseApiRequestTestBuilder.anInvalidHouseStatusRequest().build()
                        )
                )
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

    private static CustomerDataApiRequest invalidNullYearVehicleRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest()
                .vehicles(List.of(CustomerVehicleApiRequestTestBuilder.aNullVehicleYearRequest().build())).build();
    }

    private static CustomerDataApiRequest invalidYearVehicleRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest()
                .vehicles(
                        List.of(
                                CustomerVehicleApiRequestTestBuilder.aVehicleRequest().build(),
                                CustomerVehicleApiRequestTestBuilder.anInvalidVehicleYearRequest().build())
                )
                .build();
    }

    private static CustomerDataApiRequest invalidYearBiggerCurrentYearPlusTwoVehicleRequest() {
        return CustomerDataApiRequestTestBuilder.aCustomerDataRequest()
                .vehicles(List.of(
                        CustomerVehicleApiRequestTestBuilder
                                .aVehicleRequest()
                                .year(LocalDateTime.now().getYear() + 2)
                                .build()))
                .build();
    }
}
