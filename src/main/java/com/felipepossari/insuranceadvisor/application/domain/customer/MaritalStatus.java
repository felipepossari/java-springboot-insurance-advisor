package com.felipepossari.insuranceadvisor.application.domain.customer;

public enum MaritalStatus {
    SINGLE, MARRIED, DIVORCED, DOMESTIC_PARTNERSHIP;

    public static String parseMaritalStatus(String status){
        return status.replace(" ", "_").toUpperCase();
    }
}
