package com.felipepossari.insuranceadvisor.adapter.in.web.risk.v1.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerHouseApiRequest {
    private String ownershipStatus;
}
