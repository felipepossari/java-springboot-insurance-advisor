package com.felipepossari.insuranceadvisor.application.domain.rule;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;
import com.felipepossari.insuranceadvisor.application.helper.date.DateTime;

import java.util.EnumMap;

public class VehicleRule implements Rule {

    private final DateTime dateTime;

    public VehicleRule(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public void apply(Customer customer, EnumMap<InsuranceType, Insurance> insurances) {
        if (customer.hasNewVehicle(dateTime.getCurrentDate().getYear())) {
            insurances.get(InsuranceType.AUTO).addRiskPoint();
        }
    }
}
