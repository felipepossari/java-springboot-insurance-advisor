package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RiskApiErrorReason {

    REQUEST_BODY_PARSE_FAIL("R000", "Failure to parse request body"),
    FIELD_AGE_INVALID("R001","Age number invalid"),
    FIELD_DEPENDENTS_INVALID("R002","Dependents number invalid"),
    FIELD_HOUSE_INVALID("R003","House value invalid. It must be owned or mortgaged"),
    FIELD_INCOME_INVALID("R004","Income number invalid"),
    FIELD_MARITAL_STATUS_EMPTY("R005","Marital status cannot be null or empty"),
    FIELD_MARITAL_STATUS_INVALID("R006","Marital status invalid. It must be single or married"),
    FIELD_RISK_QUESTIONS_EMPTY("R007","Risk questions cannot be null or empty"),
    FIELD_RISK_QUESTIONS_INVALID_LENGTH("R008","Risk questions must have three answers"),
    FIELD_RISK_QUESTIONS_INVALID_VALUE("R009","Risk questions values invalid. They must be zero or one"),
    FIELD_VEHICLE_INVALID("R010","Vehicle year value invalid"),
    UNKNOWN_ERROR("R999","An unknown error happened");

    private String code;
    private String message;
}
