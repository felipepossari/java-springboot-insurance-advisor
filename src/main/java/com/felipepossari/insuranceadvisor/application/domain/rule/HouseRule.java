package com.felipepossari.insuranceadvisor.application.domain.rule;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;

import java.util.EnumMap;

public class HouseRule implements Rule {
    @Override
    public void apply(Customer customer, EnumMap<InsuranceType, Insurance> insurances) {
        if (customer.hasMortGagedHouse()) {
            insurances.get(InsuranceType.HOME).addRiskPoint();
            insurances.get(InsuranceType.DISABILITY).addRiskPoint();
        }
    }
}
