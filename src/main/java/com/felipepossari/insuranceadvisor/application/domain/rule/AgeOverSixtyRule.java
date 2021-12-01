package com.felipepossari.insuranceadvisor.application.domain.rule;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;

import java.util.EnumMap;

public class AgeOverSixtyRule implements Rule {
    @Override
    public void apply(Customer customer, EnumMap<InsuranceType, Insurance> insurances) {
        if(customer.hasMoreSixtyYears()){
            insurances.get(InsuranceType.DISABILITY).makeIneligibly();
            insurances.get(InsuranceType.LIFE).makeIneligibly();
        }
    }
}
