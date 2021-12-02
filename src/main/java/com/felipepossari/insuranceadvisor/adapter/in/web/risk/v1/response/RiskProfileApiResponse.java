package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class RiskProfileApiResponse {
    private String auto;
    private String disability;
    private String home;
    private String life;
}
