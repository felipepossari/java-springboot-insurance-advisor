package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class RiskProfileApiResponse {
    private String auto;
    private String disability;
    private String home;
    private String life;
}
