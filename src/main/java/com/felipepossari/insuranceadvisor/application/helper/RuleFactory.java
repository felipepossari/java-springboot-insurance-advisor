package com.felipepossari.insuranceadvisor.application.helper;

import com.felipepossari.insuranceadvisor.application.domain.rule.AgeOverSixtyRule;
import com.felipepossari.insuranceadvisor.application.domain.rule.AgeUnderFortyRule;
import com.felipepossari.insuranceadvisor.application.domain.rule.DependentRule;
import com.felipepossari.insuranceadvisor.application.domain.rule.HouseRule;
import com.felipepossari.insuranceadvisor.application.domain.rule.IncomeRule;
import com.felipepossari.insuranceadvisor.application.domain.rule.IncomeVehicleHouseEligibilityRule;
import com.felipepossari.insuranceadvisor.application.domain.rule.MaritalStatusRule;
import com.felipepossari.insuranceadvisor.application.domain.rule.Rule;
import com.felipepossari.insuranceadvisor.application.domain.rule.VehicleRule;
import com.felipepossari.insuranceadvisor.application.helper.date.DateTime;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RuleFactory {

    private final List<Rule> rules;
    private final DateTime dateTime;

    public RuleFactory(DateTime dateTime) {
        this.dateTime = dateTime;
        rules = List.of(
                new IncomeVehicleHouseEligibilityRule(),
                new AgeOverSixtyRule(),
                new AgeOverSixtyRule(),
                new AgeUnderFortyRule(),
                new IncomeRule(),
                new HouseRule(),
                new DependentRule(),
                new MaritalStatusRule(),
                new VehicleRule(this.dateTime)
        );
    }

    public List<Rule> getRules() {
        return rules;
    }
}
