package com.felipepossari.insuranceadvisor.unit.application.domain.rule;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.customer.MaritalStatus;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;
import com.felipepossari.insuranceadvisor.application.domain.rule.MaritalStatusRule;
import com.felipepossari.insuranceadvisor.base.domain.CustomerTestBuilder;
import com.felipepossari.insuranceadvisor.base.domain.EnumMapInsurancesTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumMap;

import static com.felipepossari.insuranceadvisor.application.domain.ScoreResult.ECONOMIC;
import static com.felipepossari.insuranceadvisor.application.domain.ScoreResult.REGULAR;
import static com.felipepossari.insuranceadvisor.application.domain.ScoreResult.RESPONSIBLE;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.AUTO;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.DISABILITY;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.HOME;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.LIFE;

@ExtendWith(MockitoExtension.class)
class MaritalStatusRuleTest {

    private final MaritalStatusRule rule = new MaritalStatusRule();

    @Test
    void applyShouldAddRiskPointLifeAndDeductRiskPointDisabilityWhenUserIsMarried() {
        Customer customer = CustomerTestBuilder.aCustomer()
                .maritalStatus(MaritalStatus.MARRIED)
                .baseScore(2)
                .build();

        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder
                .anInsuranceList()
                .customer(customer)
                .build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(REGULAR, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(AUTO).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(HOME).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(LIFE).getScoreResult());
    }

    @Test
    void applyShouldAddRiskPointLifeAndDeductRiskPointDisabilityWhenUserIsMarried2() {
        Customer customer = CustomerTestBuilder.aCustomer()
                .maritalStatus(MaritalStatus.MARRIED)
                .baseScore(1)
                .build();

        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder
                .anInsuranceList()
                .customer(customer)
                .build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(ECONOMIC, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(AUTO).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(HOME).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(LIFE).getScoreResult());
    }

    @Test
    void applyShouldDoNothingWhenUserHasIsNotMarried() {
        Customer customer = CustomerTestBuilder.aCustomer()
                .maritalStatus(MaritalStatus.SINGLE)
                .baseScore(3)
                .build();

        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder
                .anInsuranceList()
                .customer(customer)
                .build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(RESPONSIBLE, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(AUTO).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(HOME).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(LIFE).getScoreResult());
    }

}
