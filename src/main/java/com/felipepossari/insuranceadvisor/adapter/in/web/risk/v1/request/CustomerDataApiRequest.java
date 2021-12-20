package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CustomerDataApiRequest {
    private Integer age;
    private Integer dependents;
    private List<CustomerHouseApiRequest> houses;
    private Integer income;
    private String maritalStatus;
    private Integer[] riskQuestions;
    private List<CustomerVehicleApiRequest> vehicles;
}
