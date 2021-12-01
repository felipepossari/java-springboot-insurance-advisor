package com.felipepossari.insuranceadvisor.application.domain.rule;

import com.felipepossari.insuranceadvisor.application.domain.ScoreResult;
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

@ExtendWith(MockitoExtension.class)
class IncomeVehicleHouseEligibilityRuleTest {

    private final IncomeVehicleHouseEligibilityRule rule = new IncomeVehicleHouseEligibilityRule();

    @Test
    void applyShouldDoNothingIsCustomerHasIncomeVehicleHouse() {
        Customer customer = CustomerTestBuilder.aCustomer().build();
        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder.anInsuranceList().build();

        rule.apply(customer, insurances);

        Assertions.assertNotEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.DISABILITY).getScoreResult());
        Assertions.assertNotEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.AUTO).getScoreResult());
        Assertions.assertNotEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.HOME).getScoreResult());
    }

    @Test
    void applyShouldMakeInsurancesIneligiblyWhenCustomerHasNoIncome() {
        Customer customer = CustomerTestBuilder.aCustomer().income(0).build();
        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder.anInsuranceList().build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.DISABILITY).getScoreResult());
        Assertions.assertEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.AUTO).getScoreResult());
        Assertions.assertEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.HOME).getScoreResult());
    }

    @Test
    void applyShouldMakeInsurancesIneligiblyWhenCustomerHasNoHouse() {
        Customer customer = CustomerTestBuilder.aCustomer().house(null).build();
        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder.anInsuranceList().build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.DISABILITY).getScoreResult());
        Assertions.assertEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.AUTO).getScoreResult());
        Assertions.assertEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.HOME).getScoreResult());
    }

    @Test
    void applyShouldMakeInsurancesIneligiblyWhenCustomerHasNoVehicle() {
        Customer customer = CustomerTestBuilder.aCustomer().vehicle(null).build();
        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder.anInsuranceList().build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.DISABILITY).getScoreResult());
        Assertions.assertEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.AUTO).getScoreResult());
        Assertions.assertEquals(ScoreResult.INELIGIBLE, insurances.get(InsuranceType.HOME).getScoreResult());
    }
}
