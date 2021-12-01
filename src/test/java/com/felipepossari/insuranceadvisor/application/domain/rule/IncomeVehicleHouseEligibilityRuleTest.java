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
        Customer customer = CustomerTestBuilder.aCustomer().build();
        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder.anInsuranceList().build();

        rule.apply(customer, insurances);

        Assertions.assertNotEquals(INELIGIBLE, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertNotEquals(INELIGIBLE, insurances.get(AUTO).getScoreResult());
        Assertions.assertNotEquals(INELIGIBLE, insurances.get(HOME).getScoreResult());
        Assertions.assertNotEquals(INELIGIBLE, insurances.get(LIFE).getScoreResult());
    }

    @Test
    void applyShouldMakeInsurancesIneligiblyWhenCustomerHasNoIncome() {
        Customer customer = CustomerTestBuilder.aCustomer().income(0).build();
        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder.anInsuranceList().build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(INELIGIBLE, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertEquals(INELIGIBLE, insurances.get(AUTO).getScoreResult());
        Assertions.assertEquals(INELIGIBLE, insurances.get(HOME).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(LIFE).getScoreResult());
    }

    @Test
    void applyShouldMakeInsurancesIneligiblyWhenCustomerHasNoHouse() {
        Customer customer = CustomerTestBuilder.aCustomer().house(null).build();
        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder.anInsuranceList().build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(INELIGIBLE, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertEquals(INELIGIBLE, insurances.get(AUTO).getScoreResult());
        Assertions.assertEquals(INELIGIBLE, insurances.get(HOME).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(LIFE).getScoreResult());
    }

    @Test
    void applyShouldMakeInsurancesIneligiblyWhenCustomerHasNoVehicle() {
        Customer customer = CustomerTestBuilder.aCustomer().vehicle(null).build();
        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder.anInsuranceList().build();

        rule.apply(customer, insurances);

        Assertions.assertEquals(INELIGIBLE, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertEquals(INELIGIBLE, insurances.get(AUTO).getScoreResult());
        Assertions.assertEquals(INELIGIBLE, insurances.get(HOME).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(LIFE).getScoreResult());
    }
}
