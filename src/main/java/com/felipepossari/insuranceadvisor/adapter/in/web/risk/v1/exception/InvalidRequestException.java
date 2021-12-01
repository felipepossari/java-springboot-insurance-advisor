package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Getter
public class InvalidRequestException extends RuntimeException {
    private final transient BindingResult bindingResult;
}
