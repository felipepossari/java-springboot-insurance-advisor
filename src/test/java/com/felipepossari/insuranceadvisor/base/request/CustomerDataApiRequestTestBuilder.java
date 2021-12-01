package com.felipepossari.insuranceadvisor.base.request;

import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerDataApiRequest;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerHouseApiRequest;
import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerVehicleApiRequest;

import static com.felipepossari.insuranceadvisor.base.DefaultConstants.AGE;
import static com.felipepossari.insuranceadvisor.base.DefaultConstants.DEPENDENTS;
import static com.felipepossari.insuranceadvisor.base.DefaultConstants.INCOME;
import static com.felipepossari.insuranceadvisor.base.DefaultConstants.MARITAL_STATUS;
import static com.felipepossari.insuranceadvisor.base.DefaultConstants.RISK_QUESTIONS;

public class CustomerDataApiRequestTestBuilder {

    private Integer age = AGE;
    private Integer dependants = DEPENDENTS;
    private CustomerHouseApiRequest house = CustomerHouseApiRequestTestBuilder.anOwnedHouseRequest().build();
    private Integer income = INCOME;
    private String maritalStatus = MARITAL_STATUS;
    private Integer[] riskQuestions = RISK_QUESTIONS;
    private CustomerVehicleApiRequest vehicle = CustomerVehicleApiRequestTestBuilder.aVehicleRequest().build();

    public CustomerDataApiRequestTestBuilder() {
    }

    public CustomerDataApiRequestTestBuilder ae(Integer age) {
        this.age = age;
        return this;
    }

    public CustomerDataApiRequestTestBuilder dependants(Integer dependants) {
        this.dependants = dependants;
        return this;
    }

    public CustomerDataApiRequestTestBuilder house(CustomerHouseApiRequest house) {
        this.house = house;
        return this;
    }

    public CustomerDataApiRequestTestBuilder income(Integer income) {
        this.income = income;
        return this;
    }

    public CustomerDataApiRequestTestBuilder maritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public CustomerDataApiRequestTestBuilder riskQuestions(Integer[] riskQuestions) {
        this.riskQuestions = riskQuestions;
        return this;
    }

    public CustomerDataApiRequestTestBuilder vehicle(CustomerVehicleApiRequest vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public CustomerDataApiRequest build() {
        return CustomerDataApiRequest.builder()
                .age(age)
                .dependants(dependants)
                .house(house)
                .income(income)
                .maritalStatus(maritalStatus)
                .riskQuestions(riskQuestions)
                .vehicle(vehicle)
                .build();
    }
}
