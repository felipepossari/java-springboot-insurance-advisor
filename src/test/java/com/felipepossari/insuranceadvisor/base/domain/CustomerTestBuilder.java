package com.felipepossari.insuranceadvisor.base.domain;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.customer.House;
import com.felipepossari.insuranceadvisor.application.domain.customer.MaritalStatus;
import com.felipepossari.insuranceadvisor.base.DefaultConstants;

import java.util.Arrays;

import static com.felipepossari.insuranceadvisor.base.DefaultConstants.AGE;
import static com.felipepossari.insuranceadvisor.base.DefaultConstants.DEPENDENTS;
import static com.felipepossari.insuranceadvisor.base.DefaultConstants.INCOME;
import static com.felipepossari.insuranceadvisor.base.DefaultConstants.RISK_QUESTIONS;

public class CustomerTestBuilder {
    private Integer age = AGE;
    private Integer dependants = DEPENDENTS;
    private House house = House.OWNED;
    private Integer income = INCOME;
    private MaritalStatus maritalStatus = MaritalStatus.MARRIED;
    private int baseScore = Arrays.stream(RISK_QUESTIONS).reduce(0, Integer::sum);
    private Integer vehicleYear = DefaultConstants.VEHICLE_YEAR;

    private CustomerTestBuilder() {
    }

    public static CustomerTestBuilder aCustomer() {
        return new CustomerTestBuilder();
    }

    public CustomerTestBuilder age(Integer age) {
        this.age = age;
        return this;
    }

    public CustomerTestBuilder dependents(Integer dependants) {
        this.dependants = dependants;
        return this;
    }

    public CustomerTestBuilder house(House house) {
        this.house = house;
        return this;
    }

    public CustomerTestBuilder income(Integer income) {
        this.income = income;
        return this;
    }

    public CustomerTestBuilder maritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public CustomerTestBuilder baseScore(Integer baseScore) {
        this.baseScore = baseScore;
        return this;
    }

    public CustomerTestBuilder vehicle(Integer vehicleYear) {
        this.vehicleYear = vehicleYear;
        return this;
    }

    public Customer build() {
        return Customer.builder()
                .age(age)
                .dependents(dependants)
                .house(house)
                .income(income)
                .maritalStatus(maritalStatus)
                .baseScore(baseScore)
                .vehicleYear(vehicleYear)
                .build();
    }
}
