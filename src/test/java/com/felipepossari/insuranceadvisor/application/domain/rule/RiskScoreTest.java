package com.felipepossari.insuranceadvisor.application.domain.rule;

import com.felipepossari.insuranceadvisor.application.domain.RiskScore;
import com.felipepossari.insuranceadvisor.application.domain.ScoreResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class RiskScoreTest {

    @ParameterizedTest
    @MethodSource(value = "scores")
    void getResultScoreShouldReturnTheRightScore(int score, ScoreResult scoreResult) {
        var riskScore = new RiskScore(score);
        Assertions.assertEquals(scoreResult, riskScore.getScoreResult());
    }

    public static Stream<Arguments> scores() {
        return Stream.of(
                Arguments.arguments(-1, ScoreResult.ECONOMIC),
                Arguments.arguments(0, ScoreResult.ECONOMIC),
                Arguments.arguments(1, ScoreResult.REGULAR),
                Arguments.arguments(2, ScoreResult.REGULAR),
                Arguments.arguments(3, ScoreResult.RESPONSIBLE),
                Arguments.arguments(4, ScoreResult.RESPONSIBLE),
                Arguments.arguments(49, ScoreResult.RESPONSIBLE),
                Arguments.arguments(50, ScoreResult.RESPONSIBLE),
                Arguments.arguments(51, ScoreResult.INELIGIBLE)
        );
    }
}
