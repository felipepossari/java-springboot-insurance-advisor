package com.felipepossari.insuranceadvisor.unit.application.domain.rule;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;
import com.felipepossari.insuranceadvisor.application.domain.rule.IncomeVehicleHouseEligibilityRule;
import com.felipepossari.insuranceadvisor.base.domain.CustomerTestBuilder;
import com.felipepossari.insuranceadvisor.base.domain.EnumMapInsurancesTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumMap;

import static com.felipepossari.insuranceadvisor.application.domain.ScoreResult.INELIGIBLE;
import static com.felipepossari.insuranceadvisor.application.domain.ScoreResult.RESPONSIBLE;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.AUTO;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.DISABILITY;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.HOME;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.LIFE;

@ExtendWith(MockitoExtension.class)
class IncomeVehicleHouseEligibilityRuleTest {

    private final IncomeVehicleHouseEligibilityRule rule = new IncomeVehicleHouseEligibilityRule();

    @Test
    void applyShouldDoNothingIsCustomerHasIncomeVehicleHouse() {
        Customer customer = CustomerTestBuilder.aCustomer()
                .baseScore(3)
                .build();
        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder
                .anInsuranceList()
                .customer(customer)
                .build();

        rule.apply(customer, insurances);

        Assertions.assertNotEquals(INELIGIBLE, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertNotEquals(INELIGIBLE, insurances.get(AUTO).getScoreResult());
        Assertions.assertNotEquals(INELIGIBLE, insurances.get(HOME).getScoreResult());
        Assertions.assertNotEquals(INELIGIBLE, insurances.get(LIFE).getScoreResult());
    }

    @Test
    void applyShouldMakeDisabilityIneligiblyWhenCustomerHasNoIncome() {
        Customer customer = CustomerTestBuilder.aCustomer()
                .baseScore(3)
                .income(0)
                .build();

        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder
                .anInsuranceList()
                .customer(customer)
                .build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(INELIGIBLE, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(AUTO).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(HOME).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(LIFE).getScoreResult());
    }

    @Test
    void applyShouldMakeHomeIneligiblyWhenCustomerHasNoHouse() {
        Customer customer = CustomerTestBuilder.aCustomer()
                .baseScore(3)
                .house(null)
                .build();

        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder
                .anInsuranceList()
                .customer(customer)
                .build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(RESPONSIBLE, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(AUTO).getScoreResult());
        Assertions.assertEquals(INELIGIBLE, insurances.get(HOME).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(LIFE).getScoreResult());
    }

    @Test
    void applyShouldMakeAutoIneligiblyWhenCustomerHasNoVehicle() {
        Customer customer = CustomerTestBuilder.aCustomer()
                .baseScore(3)
                .vehicle(null)
                .build();

        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder
                .anInsuranceList()
                .customer(customer)
                .build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(RESPONSIBLE, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertEquals(INELIGIBLE, insurances.get(AUTO).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(HOME).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(LIFE).getScoreResult());
    }
}
