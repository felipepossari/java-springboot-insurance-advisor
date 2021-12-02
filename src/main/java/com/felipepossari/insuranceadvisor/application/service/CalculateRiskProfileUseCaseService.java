package com.felipepossari.insuranceadvisor.application.service;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;
import com.felipepossari.insuranceadvisor.application.domain.rule.Rule;
import com.felipepossari.insuranceadvisor.application.helper.RuleFactory;
import com.felipepossari.insuranceadvisor.application.port.in.CalculateRiskProfileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CalculateRiskProfileUseCaseService implements CalculateRiskProfileUseCase {

    private final RuleFactory ruleFactory;

    @Override
    public EnumMap<InsuranceType, Insurance> calculate(Customer customer) {
        var insurances = fillInsurancesScore(customer);
        applyRules(customer, insurances, ruleFactory.getRules());
        return insurances;
    }

    private void applyRules(Customer customer, EnumMap<InsuranceType, Insurance> insurances, List<Rule> rules) {
        rules.forEach(rule -> rule.apply(customer, insurances));
    }

    private EnumMap<InsuranceType, Insurance> fillInsurancesScore(Customer customer) {
        EnumMap<InsuranceType, Insurance> insurances = new EnumMap<>(InsuranceType.class);
        Stream.of(InsuranceType.values()).forEach(i ->
                insurances.put(i, new Insurance(i, customer.getBaseScore()))
        );
        return insurances;
    }
}
