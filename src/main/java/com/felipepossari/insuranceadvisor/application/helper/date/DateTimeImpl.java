package com.felipepossari.insuranceadvisor.application.helper.date;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
class DateTimeImpl implements DateTime {

    @Override
    public LocalDateTime getCurrentDate() {
        return LocalDateTime.now();
    }
}
