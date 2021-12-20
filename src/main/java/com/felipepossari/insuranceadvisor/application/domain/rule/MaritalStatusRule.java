package com.felipepossari.insuranceadvisor.application.domain.rule;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;

import java.util.EnumMap;

public class MaritalStatusRule implements Rule{
    @Override
    public void apply(Customer customer, EnumMap<InsuranceType, Insurance> insurances) {
        if (customer.isMarried() || customer.hasDomesticPartnershipRelation()) {
            insurances.get(InsuranceType.LIFE).addRiskPoint();
            insurances.get(InsuranceType.DISABILITY).deductRiskPoint();
        }
    }
}
