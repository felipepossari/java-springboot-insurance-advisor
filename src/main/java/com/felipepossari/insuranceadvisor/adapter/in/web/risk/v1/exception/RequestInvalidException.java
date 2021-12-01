package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RequestInvalidException extends RuntimeException{
    private final RiskApiErrorReason riskApiErrorReason;
}
