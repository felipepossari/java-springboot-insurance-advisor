package com.felipepossari.insuranceadvisor.application.domain.rule;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;

import java.util.EnumMap;

public class IncomeVehicleHouseEligibilityRule implements Rule {

    @Override
    public void apply(Customer customer, EnumMap<InsuranceType, Insurance> insurances) {
        if (!customer.hasIncome() || !customer.hasVehicle() || !customer.hasHouse()) {
            insurances.get(InsuranceType.DISABILITY).makeIneligibly();
            insurances.get(InsuranceType.AUTO).makeIneligibly();
            insurances.get(InsuranceType.HOME).makeIneligibly();
        }
    }
}
