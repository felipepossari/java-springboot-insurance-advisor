package com.felipepossari.insuranceadvisor.unit.application.domain.rule;

import com.felipepossari.insuranceadvisor.application.domain.ScoreResult;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;
import com.felipepossari.insuranceadvisor.application.domain.rule.RiskAnswerFalseAndLowIncomeRule;
import com.felipepossari.insuranceadvisor.base.domain.CustomerTestBuilder;
import com.felipepossari.insuranceadvisor.base.domain.EnumMapInsurancesTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
class RiskAnswerFalseAndLowIncomeRuleTest {

    private final RiskAnswerFalseAndLowIncomeRule rule = new RiskAnswerFalseAndLowIncomeRule();

    @Test
    void applyShouldMakeAllInsurancesIneligibleWhenAllAnswersAreFalseAndIncomeLowerThan25k() {
        // given
        var customer = CustomerTestBuilder.aCustomerWithAllAnswersFalse()
                .income(24999)
                .build();

        var insurances = EnumMapInsurancesTestBuilder
                .anInsuranceList().customer(customer).build();

        // then
        rule.apply(customer, insurances);

        // when
        assertEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.AUTO).getScoreResult());
        assertEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.HOME).getScoreResult());
        assertEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.LIFE).getScoreResult());
        assertEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.DISABILITY).getScoreResult());
    }

    @Test
    void applyShouldDoNothingWhenOneAnswerIsTrue() {
        // given
        var customer = CustomerTestBuilder.aCustomer()
                .baseScore(1)
                .income(24999)
                .build();

        var insurances = EnumMapInsurancesTestBuilder
                .anInsuranceList().customer(customer).build();

        // then
        rule.apply(customer, insurances);

        // when
        assertNotEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.AUTO).getScoreResult());
        assertNotEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.HOME).getScoreResult());
        assertNotEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.LIFE).getScoreResult());
        assertNotEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.DISABILITY).getScoreResult());
    }

    @Test
    void applyShouldDoNothingWhenIncomeIsHigherThan25k() {
        // given
        var customer = CustomerTestBuilder.aCustomerWithAllAnswersFalse()
                .income(25000)
                .build();

        var insurances = EnumMapInsurancesTestBuilder
                .anInsuranceList().customer(customer).build();

        // then
        rule.apply(customer, insurances);

        // when
        assertNotEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.AUTO).getScoreResult());
        assertNotEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.HOME).getScoreResult());
        assertNotEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.LIFE).getScoreResult());
        assertNotEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.DISABILITY).getScoreResult());
    }
}
