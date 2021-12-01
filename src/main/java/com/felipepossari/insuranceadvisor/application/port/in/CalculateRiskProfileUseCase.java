package com.felipepossari.insuranceadvisor.application.port.in;

import com.felipepossari.insuranceadvisor.application.domain.Customer;
import com.felipepossari.insuranceadvisor.application.domain.RiskProfile;

public interface CalculateRiskProfileUseCase {

    RiskProfile calculate(Customer customer);
}
