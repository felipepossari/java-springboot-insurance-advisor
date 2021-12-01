package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1;

import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.InvalidRequestException;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerDataApiRequest;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.response.RiskProfileApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/risks")
@RequiredArgsConstructor
public class RiskController {

    private final RiskRequestValidator riskRequestValidator;

    @PostMapping
    public ResponseEntity<RiskProfileApiResponse> postRisk(
            @Valid @RequestBody CustomerDataApiRequest customerDataApiRequest,
            BindingResult bindingResult) {
        checkRequest(bindingResult);
        return ResponseEntity.ok(RiskProfileApiResponse.builder().name("a").build());
    }

    private void checkRequest(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }
    }

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(riskRequestValidator);
    }
}
