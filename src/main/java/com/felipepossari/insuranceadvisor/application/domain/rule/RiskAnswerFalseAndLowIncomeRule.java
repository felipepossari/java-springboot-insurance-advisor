package com.felipepossari.insuranceadvisor.application.domain.rule;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;

import java.util.EnumMap;

import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.AUTO;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.DISABILITY;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.HOME;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.LIFE;

public class RiskAnswerFalseAndLowIncomeRule implements Rule {
    @Override
    public void apply(Customer customer, EnumMap<InsuranceType, Insurance> insurances) {
        if (customer.areAllAnswersFalse()
                && customer.hasIncomeLowerThanTwentyFiveThousands()) {
            insurances.get(AUTO).makeIneligibly();
            insurances.get(LIFE).makeIneligibly();
            insurances.get(DISABILITY).makeIneligibly();
            insurances.get(HOME).makeIneligibly();
        }
    }
}
