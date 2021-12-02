package com.felipepossari.insuranceadvisor.application.domain.rule;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;
import com.felipepossari.insuranceadvisor.base.domain.CustomerTestBuilder;
import com.felipepossari.insuranceadvisor.base.domain.EnumMapInsurancesTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumMap;

import static com.felipepossari.insuranceadvisor.application.domain.ScoreResult.REGULAR;
import static com.felipepossari.insuranceadvisor.application.domain.ScoreResult.RESPONSIBLE;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.AUTO;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.DISABILITY;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.HOME;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.LIFE;

@ExtendWith(MockitoExtension.class)
class IncomeRuleTest {

    private final IncomeRule rule = new IncomeRule();

    @ParameterizedTest
    @ValueSource(ints = {200000, 300000})
    void applyShouldDeductRiskPointWhenUserIncomeIsBiggerThanTwoHundredK(int income) {
        Customer customer = CustomerTestBuilder.aCustomer().income(income).build();
        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder.anInsuranceList().build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(REGULAR, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(AUTO).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(HOME).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(LIFE).getScoreResult());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 199999})
    void applyShouldDoNothingWhenUserIncomeIsLowerThanTwoHundredK(int income) {
        Customer customer = CustomerTestBuilder.aCustomer().income(income).build();
        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder.anInsuranceList().build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(RESPONSIBLE, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(AUTO).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(HOME).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(LIFE).getScoreResult());
    }
}
