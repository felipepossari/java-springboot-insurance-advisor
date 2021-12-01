package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1;

import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerDataApiRequest;
import com.felipepossari.insuranceadvisor.application.domain.House;
import com.felipepossari.insuranceadvisor.application.domain.MaritalStatus;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.util.Arrays;

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

@Component
public class RiskRequestValidator implements Validator {

    public static final int QUESTIONS_NUMBER = 3;
    public static final int QUESTION_NEGATIVE_ANSWER = 0;
    public static final int QUESTION_POSITIVE_ANSWER = 1;

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerDataApiRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerDataApiRequest request = (CustomerDataApiRequest) target;

        validateAge(errors, request);
        validateDependents(errors, request);
        validateHouse(errors, request);
        validateIncome(errors, request);
        validateMaritalStatus(errors, request);
        validateRiskQuestions(errors, request);
        validateVehicle(errors, request);
    }

    private void validateVehicle(Errors errors, CustomerDataApiRequest request) {
        if (ObjectUtils.isEmpty(request.getVehicle())) {
            errors.reject(FIELD_VEHICLE_EMPTY.getCode(), FIELD_VEHICLE_EMPTY.getMessage());
        } else if (ObjectUtils.isEmpty(request.getVehicle().getYear())
                || (request.getVehicle().getYear() < 0 || request.getVehicle().getYear() >
                LocalDateTime.now().getYear() + 1)) {
            errors.reject(FIELD_VEHICLE_INVALID.getCode(), FIELD_VEHICLE_INVALID.getMessage());
        }
    }

    private void validateRiskQuestions(Errors errors, CustomerDataApiRequest request) {
        if (ObjectUtils.isEmpty(request.getRiskQuestions())) {
            errors.reject(FIELD_RISK_QUESTIONS_EMPTY.getCode(), FIELD_RISK_QUESTIONS_EMPTY.getMessage());
        } else if (request.getRiskQuestions().length != QUESTIONS_NUMBER) {
            errors.reject(FIELD_RISK_QUESTIONS_INVALID_LENGTH.getCode(), FIELD_RISK_QUESTIONS_EMPTY.getMessage());
        } else if (hasInvalidAnswerValue(request)) {
            errors.reject(FIELD_RISK_QUESTIONS_INVALID_VALUE.getCode(),
                    FIELD_RISK_QUESTIONS_INVALID_VALUE.getMessage());
        }
    }

    private void validateMaritalStatus(Errors errors, CustomerDataApiRequest request) {
        if (StringUtils.isEmpty(request.getMaritalStatus())) {
            errors.reject(FIELD_MARITAL_STATUS_EMPTY.getCode(), FIELD_MARITAL_STATUS_EMPTY.getMessage());
        } else if (!EnumUtils.isValidEnum(MaritalStatus.class, request.getMaritalStatus().toUpperCase())) {
            errors.reject(FIELD_MARITAL_STATUS_INVALID.getCode(), FIELD_MARITAL_STATUS_INVALID.getMessage());
        }
    }

    private void validateIncome(Errors errors, CustomerDataApiRequest request) {
        if (ObjectUtils.isEmpty(request.getIncome()) || request.getIncome() < 0) {
            errors.reject(FIELD_INCOME_INVALID.getCode(), FIELD_INCOME_INVALID.getMessage());
        }
    }

    private void validateHouse(Errors errors, CustomerDataApiRequest request) {
        if (ObjectUtils.isEmpty(request.getHouse()) || StringUtils.isEmpty(request.getHouse().getOwnershipStatus())) {
            errors.reject(FIELD_HOUSE_EMPTY.getCode(), FIELD_HOUSE_EMPTY.getMessage());
        } else if (!EnumUtils.isValidEnum(House.class, request.getHouse().getOwnershipStatus().toUpperCase())) {
            errors.reject(FIELD_HOUSE_INVALID.getCode(), FIELD_HOUSE_INVALID.getMessage());
        }
    }

    private void validateDependents(Errors errors, CustomerDataApiRequest request) {
        if (ObjectUtils.isEmpty(request.getDependents()) || request.getDependents() < 0) {
            errors.reject(FIELD_DEPENDENTS_INVALID.getCode(), FIELD_DEPENDENTS_INVALID.getMessage());
        }
    }

    private void validateAge(Errors errors, CustomerDataApiRequest request) {
        if (ObjectUtils.isEmpty(request.getAge()) || request.getAge() < 0) {
            errors.reject(FIELD_AGE_INVALID.getCode(), FIELD_AGE_INVALID.getMessage());
        }
    }

    private boolean hasInvalidAnswerValue(CustomerDataApiRequest request) {
        return Arrays.stream(request.getRiskQuestions())
                .anyMatch(answer -> answer != QUESTION_NEGATIVE_ANSWER && answer != QUESTION_POSITIVE_ANSWER);
    }
}
