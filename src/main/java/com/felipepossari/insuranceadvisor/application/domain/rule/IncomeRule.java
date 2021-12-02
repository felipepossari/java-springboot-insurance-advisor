package com.felipepossari.insuranceadvisor.application.domain.rule;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;

import java.util.EnumMap;

public class IncomeRule implements Rule{
    @Override
    public void apply(Customer customer, EnumMap<InsuranceType, Insurance> insurances) {
        if (customer.isIncomeBiggerTwoHundreadK()) {
            insurances.get(InsuranceType.AUTO).deductRiskPoint();
            insurances.get(InsuranceType.DISABILITY).deductRiskPoint();
            insurances.get(InsuranceType.HOME).deductRiskPoint();
            insurances.get(InsuranceType.LIFE).deductRiskPoint();
        }
    }
}
