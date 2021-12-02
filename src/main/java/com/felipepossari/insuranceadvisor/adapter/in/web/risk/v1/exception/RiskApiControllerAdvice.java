package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.REQUEST_BODY_PARSE_FAIL;
import static com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.RiskApiErrorReason.UNKNOWN_ERROR;

@ControllerAdvice
public class RiskApiControllerAdvice {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<List<ErrorResponse>> handleInvalidRequest(InvalidRequestException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildErrorsResponse(ex.getBindingResult().getAllErrors()));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<List<ErrorResponse>> handleInvalidFormat(InvalidFormatException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(List.of(
                        ErrorResponse.builder()
                                .code(REQUEST_BODY_PARSE_FAIL.getCode())
                                .message(REQUEST_BODY_PARSE_FAIL.getMessage())
                                .build())
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<ErrorResponse>> handleUnknownError(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(List.of(
                        ErrorResponse.builder()
                                .code(UNKNOWN_ERROR.getCode())
                                .message(UNKNOWN_ERROR.getMessage())
                                .build())
                );
    }

    private List<ErrorResponse> buildErrorsResponse(List<ObjectError> allErrors) {
        return allErrors.stream().map(this::buildErrorResponse)
                .collect(Collectors.toList());
    }

    private ErrorResponse buildErrorResponse(ObjectError error) {
        return ErrorResponse.builder()
                .code(error.getCode())
                .message(error.getDefaultMessage())
                .build();
    }
}
