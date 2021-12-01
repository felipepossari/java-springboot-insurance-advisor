package com.felipepossari.insuranceadvisor.application.domain.insurance;

import com.felipepossari.insuranceadvisor.application.domain.RiskScore;
import com.felipepossari.insuranceadvisor.application.domain.ScoreResult;

public class Insurance {

    private final InsuranceType type;
    private RiskScore score;

    public Insurance(InsuranceType type, Integer score) {
        this.type = type;
        this.score = new RiskScore(score);
    }

    public void addRiskPoint() {
        score.addRiskPoint();
    }

    public void addTwoRiskPoints() {
        score.addTwoRiskPoints();
    }

    public void deductRiskPoint() {
        score.deductRiskPoint();
    }

    public void deductTwoRiskPoints() {
        score.deductTwoRiskPoints();
    }

    public void makeIneligibly() {
        score.applyIneligibility();
    }

    public ScoreResult getScoreResult() {
        return score.getScoreResult();
    }

    public InsuranceType getType() {
        return type;
    }
}
