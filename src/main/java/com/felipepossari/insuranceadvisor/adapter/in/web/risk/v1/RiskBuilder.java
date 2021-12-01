package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1;

import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerDataApiRequest;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.response.RiskProfileApiResponse;
import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.customer.House;
import com.felipepossari.insuranceadvisor.application.domain.customer.MaritalStatus;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.EnumMap;

import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.AUTO;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.DISABILITY;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.HOME;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.LIFE;

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

    public RiskProfileApiResponse buildResponse(EnumMap<InsuranceType, Insurance> insurances) {
        return RiskProfileApiResponse.builder()
                .auto(insurances.get(AUTO).getScoreResult().name().toLowerCase())
                .disability(insurances.get(DISABILITY).getScoreResult().name().toLowerCase())
                .home(insurances.get(HOME).getScoreResult().name().toLowerCase())
                .life(insurances.get(LIFE).getScoreResult().name().toLowerCase())
                .build();
    }
}
