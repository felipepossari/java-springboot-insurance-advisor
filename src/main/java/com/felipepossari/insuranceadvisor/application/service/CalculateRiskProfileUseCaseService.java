package com.felipepossari.insuranceadvisor.application.service;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;
import com.felipepossari.insuranceadvisor.application.domain.rule.IncomeVehicleHouseEligibilityRule;
import com.felipepossari.insuranceadvisor.application.port.in.CalculateRiskProfileUseCase;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.stream.Stream;

@Service
public class CalculateRiskProfileUseCaseService implements CalculateRiskProfileUseCase {
    @Override
    public EnumMap<InsuranceType, Insurance> calculate(Customer customer) {

        EnumMap<InsuranceType, Insurance> insurances = new EnumMap<>(InsuranceType.class);

        Stream.of(InsuranceType.values()).forEach(i ->
                insurances.put(i, new Insurance(i, customer.getBaseScore()))
        );

        IncomeVehicleHouseEligibilityRule rule = new IncomeVehicleHouseEligibilityRule();
        rule.apply(customer, insurances);

        return insurances;
    }
}
