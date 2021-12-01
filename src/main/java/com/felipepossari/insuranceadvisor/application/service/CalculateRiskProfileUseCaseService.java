package com.felipepossari.insuranceadvisor.application.service;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;
import com.felipepossari.insuranceadvisor.application.domain.rule.AgeOverSixtyRule;
import com.felipepossari.insuranceadvisor.application.domain.rule.IncomeVehicleHouseEligibilityRule;
import com.felipepossari.insuranceadvisor.application.domain.rule.Rule;
import com.felipepossari.insuranceadvisor.application.port.in.CalculateRiskProfileUseCase;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CalculateRiskProfileUseCaseService implements CalculateRiskProfileUseCase {
    @Override
    public EnumMap<InsuranceType, Insurance> calculate(Customer customer) {
        var insurances = fillInsurancesScore(customer);
        var rules = buildRuleList();
        applyRules(customer, insurances, rules);
        return insurances;
    }

    private void applyRules(Customer customer, EnumMap<InsuranceType, Insurance> insurances, List<Rule> rules) {
        rules.forEach(rule -> rule.apply(customer, insurances));
    }

    private List<Rule>  buildRuleList() {
        return List.of(
                new IncomeVehicleHouseEligibilityRule(),
                new AgeOverSixtyRule()
        );
    }

    private EnumMap<InsuranceType, Insurance> fillInsurancesScore(Customer customer) {
        EnumMap<InsuranceType, Insurance> insurances = new EnumMap<>(InsuranceType.class);
        Stream.of(InsuranceType.values()).forEach(i ->
                insurances.put(i, new Insurance(i, customer.getBaseScore()))
        );
        return insurances;
    }
}
