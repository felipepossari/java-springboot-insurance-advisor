package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RiskApiErrorReason {

    FIELD_AGE_INVALID("R001","Age numbmer invalid"),
    FIELD_DEPENDENTS_INVALID("R002","Dependents number invalid"),
    FIELD_HOUSE_EMPTY("R003","House cannot be null or empty"),
    FIELD_HOUSE_INVALID("R004","House value invalid. It must be owned or mortgaged"),
    FIELD_INCOME_INVALID("R005","Income number invalid"),
    FIELD_MARITAL_STATUS_EMPTY("R006","Marital status cannot be null or empty"),
    FIELD_MARITAL_STATUS_INVALID("R007","Marital status invalid. It must be single or married"),
    FIELD_RISK_QUESTIONS_EMPTY("R008","Risk questions cannot be null or empty"),
    FIELD_RISK_QUESTIONS_INVALID_LENGTH("R009","Risk questions must have three answers"),
    FIELD_RISK_QUESTIONS_INVALID_VALUE("R010","Risk questions values invalid. They must be zero or one"),
    FIELD_VEHICLE_EMPTY("R011","Vehicle cannot be null or empty"),
    FIELD_VEHICLE_INVALID("R012","Vehicle year value invalid");

    private String code;
    private String message;
}
