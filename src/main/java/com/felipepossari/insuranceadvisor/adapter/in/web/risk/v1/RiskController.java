package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1;

import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.exception.InvalidRequestException;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerDataApiRequest;
import com.felipepossari.insuranceadvisor.application.port.in.CalculateRiskProfileUseCase;
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
import java.util.Map;

import static com.felipepossari.insuranceadvisor.adapter.AdapterConstants.ENDPOINT_RISKS;

@RestController
@RequestMapping(path = ENDPOINT_RISKS)
@RequiredArgsConstructor
public class RiskController {

    private final RiskRequestValidator riskRequestValidator;
    private final RiskBuilder builder;
    private final CalculateRiskProfileUseCase useCase;

    @PostMapping
    public ResponseEntity<Map<String, String>> postRisk(
            @Valid @RequestBody CustomerDataApiRequest customerDataApiRequest,
            BindingResult bindingResult) {
        checkRequest(bindingResult);
        var customer = builder.buildCustomer(customerDataApiRequest);
        var insurances = useCase.calculate(customer);
        return ResponseEntity.ok(builder.buildResponse(insurances));
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
