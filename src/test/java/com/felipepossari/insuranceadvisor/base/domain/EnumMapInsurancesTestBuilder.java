package com.felipepossari.insuranceadvisor.base.domain;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;

import java.util.EnumMap;
import java.util.stream.Stream;

public class EnumMapInsurancesTestBuilder {

    private EnumMap<InsuranceType, Insurance> insurances = new EnumMap<>(InsuranceType.class);
    private Customer customer = CustomerTestBuilder.aCustomer().build();

    private EnumMapInsurancesTestBuilder() {
    }

    public static EnumMapInsurancesTestBuilder anInsuranceList() {
        return new EnumMapInsurancesTestBuilder();
    }

    public EnumMapInsurancesTestBuilder customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public EnumMap<InsuranceType, Insurance> build() {
        Stream.of(InsuranceType.values()).forEach(i ->
                insurances.put(i, new Insurance(i, customer.getBaseScore()))
        );
        return insurances;
    }
}
