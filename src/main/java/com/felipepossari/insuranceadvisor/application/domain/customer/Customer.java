package com.felipepossari.insuranceadvisor.application.domain.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
    private int age;
    private int dependents;
    private House house;
    private int income;
    private MaritalStatus maritalStatus;
    private int baseScore;
    private Integer vehicleYear;

    public boolean hasIncome(){
        return income > 0;
    }

    public boolean hasVehicle(){
        return true;
    }
}
