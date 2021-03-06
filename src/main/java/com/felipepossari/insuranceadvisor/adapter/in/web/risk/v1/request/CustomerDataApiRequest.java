package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CustomerDataApiRequest {
    private Integer age;
    private Integer dependents;
    private CustomerHouseApiRequest house;
    private Integer income;
    private String maritalStatus;
    private Integer[] riskQuestions;
    private CustomerVehicleApiRequest vehicle;
}
