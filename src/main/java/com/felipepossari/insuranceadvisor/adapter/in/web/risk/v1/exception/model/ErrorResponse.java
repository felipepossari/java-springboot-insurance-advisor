package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
    private String code;
    private String message;
}
