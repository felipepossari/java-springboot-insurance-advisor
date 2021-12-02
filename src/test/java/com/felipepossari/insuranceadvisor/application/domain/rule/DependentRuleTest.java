package com.felipepossari.insuranceadvisor.application.domain.rule;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;
import com.felipepossari.insuranceadvisor.base.domain.CustomerTestBuilder;
import com.felipepossari.insuranceadvisor.base.domain.EnumMapInsurancesTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumMap;

import static com.felipepossari.insuranceadvisor.application.domain.ScoreResult.REGULAR;
import static com.felipepossari.insuranceadvisor.application.domain.ScoreResult.RESPONSIBLE;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.AUTO;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.DISABILITY;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.HOME;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.LIFE;

@ExtendWith(MockitoExtension.class)
class DependentRuleTest {

    private final DependentRule rule = new DependentRule();

    @Test
    void applyShouldAddRiskPointWhenUserHasDependents() {
        Customer customer = CustomerTestBuilder.aCustomer()
                .baseScore(2)
                .dependents(1)
                .build();
        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder
                .anInsuranceList()
                .customer(customer)
                .build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(RESPONSIBLE, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(AUTO).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(HOME).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(LIFE).getScoreResult());
    }

    @Test
    void applyShouldDoNothingWhenUserHasNoDependents() {
        Customer customer = CustomerTestBuilder.aCustomer()
                .baseScore(2)
                .dependents(0)
                .build();
        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder
                .anInsuranceList()
                .customer(customer)
                .build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(REGULAR, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(AUTO).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(HOME).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(LIFE).getScoreResult());
    }

}
