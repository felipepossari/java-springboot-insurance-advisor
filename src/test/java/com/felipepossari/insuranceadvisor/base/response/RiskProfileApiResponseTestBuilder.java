package com.felipepossari.insuranceadvisor.base.response;

import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.response.RiskProfileApiResponse;
import com.felipepossari.insuranceadvisor.application.domain.ScoreResult;

import static com.felipepossari.insuranceadvisor.base.DefaultConstants.RESPONSE_API_STATUS;

public class RiskProfileApiResponseTestBuilder {
    private String auto = RESPONSE_API_STATUS;
    private String disability = RESPONSE_API_STATUS;
    private String home = RESPONSE_API_STATUS;
    private String life = RESPONSE_API_STATUS;

    private RiskProfileApiResponseTestBuilder() {
    }

    public static RiskProfileApiResponseTestBuilder aRiskProfileApiResponse() {
        return new RiskProfileApiResponseTestBuilder();
    }

    public RiskProfileApiResponseTestBuilder auto(ScoreResult auto) {
        this.auto = auto.name().toLowerCase();
        return this;
    }

    public RiskProfileApiResponseTestBuilder disability(ScoreResult disability) {
        this.disability = disability.name().toLowerCase();
        return this;
    }

    public RiskProfileApiResponseTestBuilder home(ScoreResult home) {
        this.home = home.name().toLowerCase();
        return this;
    }

    public RiskProfileApiResponseTestBuilder life(ScoreResult life) {
        this.life = life.name().toLowerCase();
        return this;
    }

    public RiskProfileApiResponse build() {
        return RiskProfileApiResponse.builder()
                .life(life)
                .home(home)
                .disability(disability)
                .auto(auto)
                .build();
    }
}
