package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1;

import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerDataApiRequest;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.response.RiskProfileApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/risks")
public class RiskController {

    @PostMapping
    public ResponseEntity<RiskProfileApiResponse> postRisk(
            @RequestBody CustomerDataApiRequest customerDataApiRequest) {
        return ResponseEntity.ok(RiskProfileApiResponse.builder().name(customerDataApiRequest.getName()).build());
    }
}
