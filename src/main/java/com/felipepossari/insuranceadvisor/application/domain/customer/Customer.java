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

    public boolean hasIncome() {
        return income > 0;
    }

    public boolean hasVehicle() {
        return vehicleYear != null;
    }

    public boolean hasHouse() {
        return house != null;
    }

    public boolean hasMoreSixtyYears() {
        return age > 60;
    }

    public boolean hasBetweenThirtyAndFortyYears() {
        return age >= 30 && age <= 40;
    }

    public boolean hasLessThirtyYears() {
        return age < 30;
    }

    public boolean isIncomeBiggerTwoHundreadK() {
        return income >= 200000;
    }

    public boolean hasMortGagedHouse() {
        return House.MORTGAGED.equals(this.house);
    }
}
