package com.felipepossari.insuranceadvisor.application.domain;

public class RiskScore {
    private Integer score;

    public RiskScore(Integer score) {
        this.score = score;
    }

    public ScoreResult getScoreResult() {
        if (score <= 0) {
            return ScoreResult.ECONOMIC;
        } else if (score == 1 || score == 2) {
            return ScoreResult.REGULAR;
        } else if (score <= 50) {
            return ScoreResult.RESPONSIBLE;
        } else {
            return ScoreResult.INELIGIBLE;
        }
    }

    public void addRiskPoint() {
        this.score++;
    }

    public void addTwoRiskPoints() {
        this.score += 2;
    }

    public void applyIneligibility() {
        this.score += 100;
    }

    public void deductRiskPoint() {
        this.score--;
    }

    public void deductTwoRiskPoints() {
        this.score -= 2;
    }
}
