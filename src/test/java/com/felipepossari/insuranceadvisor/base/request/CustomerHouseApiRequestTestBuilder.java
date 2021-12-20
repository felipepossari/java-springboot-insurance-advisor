package com.felipepossari.insuranceadvisor.base.request;

import com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request.CustomerHouseApiRequest;

import static com.felipepossari.insuranceadvisor.base.DefaultConstants.HOUSE_ID;
import static com.felipepossari.insuranceadvisor.base.DefaultConstants.HOUSE_OWNERSHIP_STATUS_INVALID;
import static com.felipepossari.insuranceadvisor.base.DefaultConstants.HOUSE_OWNERSHIP_STATUS_MORTGAGED;
import static com.felipepossari.insuranceadvisor.base.DefaultConstants.HOUSE_OWNERSHIP_STATUS_OWNED;

public class CustomerHouseApiRequestTestBuilder {
    private Integer id = HOUSE_ID;
    private String ownershipStatus = HOUSE_OWNERSHIP_STATUS_OWNED;

    private CustomerHouseApiRequestTestBuilder() {
    }

    public static CustomerHouseApiRequestTestBuilder anOwnedHouseRequest() {
        return new CustomerHouseApiRequestTestBuilder();
    }

    public static CustomerHouseApiRequestTestBuilder aMortgagedHouseRequest() {
        return new CustomerHouseApiRequestTestBuilder()
                .ownershipStatus(HOUSE_OWNERSHIP_STATUS_MORTGAGED);
    }

    public static CustomerHouseApiRequestTestBuilder anInvalidHouseStatusRequest() {
        return new CustomerHouseApiRequestTestBuilder()
                .ownershipStatus(HOUSE_OWNERSHIP_STATUS_INVALID);
    }

    public static CustomerHouseApiRequestTestBuilder anEmptyHouseStatusRequest() {
        return new CustomerHouseApiRequestTestBuilder()
                .ownershipStatus("");
    }

    public CustomerHouseApiRequestTestBuilder id(Integer id) {
        this.id = id;
        return this;
    }

    public CustomerHouseApiRequestTestBuilder ownershipStatus(String ownershipStatus) {
        this.ownershipStatus = ownershipStatus;
        return this;
    }

    public CustomerHouseApiRequest build() {
        return CustomerHouseApiRequest.builder()
                .id(id)
                .ownershipStatus(ownershipStatus)
                .build();
    }
}
