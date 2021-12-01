package com.felipepossari.insuranceadvisor.base.request;

import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerVehicleApiRequest;
import com.felipepossari.insuranceadvisor.base.DefaultConstants;

import static com.felipepossari.insuranceadvisor.base.DefaultConstants.VEHICLE_YEAR;

public class CustomerVehicleApiRequestTestBuilder {
    private Integer year = VEHICLE_YEAR;

    private CustomerVehicleApiRequestTestBuilder() {
    }

    public static CustomerVehicleApiRequestTestBuilder aVehicleRequest() {
        return new CustomerVehicleApiRequestTestBuilder();
    }

    public static CustomerVehicleApiRequestTestBuilder anInvalidVehicleYearRequest() {
        return new CustomerVehicleApiRequestTestBuilder()
                .year(DefaultConstants.VEHICLE_YEAR_INVALID);
    }

    public static CustomerVehicleApiRequestTestBuilder aNullVehicleYearRequest() {
        return new CustomerVehicleApiRequestTestBuilder()
                .year(null);
    }

    public CustomerVehicleApiRequestTestBuilder year(Integer year) {
        this.year = year;
        return this;
    }

    public CustomerVehicleApiRequest build() {
        return CustomerVehicleApiRequest.builder()
                .year(year)
                .build();
    }
}
