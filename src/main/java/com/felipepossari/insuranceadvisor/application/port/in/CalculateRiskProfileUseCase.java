package com.felipepossari.insuranceadvisor.application.port.in;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;

import java.util.EnumMap;

public interface CalculateRiskProfileUseCase {

    EnumMap<InsuranceType, Insurance> calculate(Customer customer);
}
