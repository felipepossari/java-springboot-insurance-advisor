package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1;

import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerDataApiRequest;
import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.customer.House;
import com.felipepossari.insuranceadvisor.application.domain.customer.MaritalStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RiskBuilder {

    public Customer buildCustomer(CustomerDataApiRequest request) {
        return Customer.builder()
                .age(request.getAge())
                .house(request.getHouse() != null ? House.valueOf(request.getHouse().getOwnershipStatus().toUpperCase()) : null)
                .maritalStatus(MaritalStatus.valueOf(request.getMaritalStatus().toUpperCase()))
                .dependents(request.getDependents())
                .income(request.getIncome())
                .vehicleYear(request.getVehicle() != null ? request.getVehicle().getYear() : null)
                .baseScore(Arrays.stream(request.getRiskQuestions()).reduce(0, Integer::sum))
                .build();
    }
}
