package com.felipepossari.insuranceadvisor.application.domain.rule;

import com.felipepossari.insuranceadvisor.application.domain.customer.Customer;
import com.felipepossari.insuranceadvisor.application.domain.insurance.Insurance;
import com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType;
import com.felipepossari.insuranceadvisor.application.helper.date.DateTime;
import com.felipepossari.insuranceadvisor.base.domain.CustomerTestBuilder;
import com.felipepossari.insuranceadvisor.base.domain.EnumMapInsurancesTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.EnumMap;

import static com.felipepossari.insuranceadvisor.application.domain.ScoreResult.REGULAR;
import static com.felipepossari.insuranceadvisor.application.domain.ScoreResult.RESPONSIBLE;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.AUTO;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.DISABILITY;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.HOME;
import static com.felipepossari.insuranceadvisor.application.domain.insurance.InsuranceType.LIFE;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VehicleRuleTest {

    @Mock
    private DateTime dateTime;

    @InjectMocks
    private VehicleRule rule;

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 2, 3, 4, 5})
    void applyShouldAddRiskPointAutoWhenUserHasNewCar(int year) {
        Customer customer = CustomerTestBuilder.aCustomer()
                .vehicle(LocalDateTime.now().getYear() - year)
                .baseScore(2)
                .build();
        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder
                .anInsuranceList()
                .customer(customer)
                .build();

        given(dateTime.getCurrentDate()).willReturn(LocalDateTime.now());

        rule.apply(customer, insurances);

        Assertions.assertEquals(REGULAR, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertEquals(RESPONSIBLE, insurances.get(AUTO).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(HOME).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(LIFE).getScoreResult());
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 10, 15})
    void applyShouldADoNothingWhenUserHasOldCar(int year) {
        Customer customer = CustomerTestBuilder.aCustomer()
                .vehicle(LocalDateTime.now().getYear() - year)
                .baseScore(2)
                .build();
        EnumMap<InsuranceType, Insurance> insurances = EnumMapInsurancesTestBuilder
                .anInsuranceList()
                .customer(customer)
                .build();

        given(dateTime.getCurrentDate()).willReturn(LocalDateTime.now());

        rule.apply(customer, insurances);

        Assertions.assertEquals(REGULAR, insurances.get(DISABILITY).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(AUTO).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(HOME).getScoreResult());
        Assertions.assertEquals(REGULAR, insurances.get(LIFE).getScoreResult());
    }

}
