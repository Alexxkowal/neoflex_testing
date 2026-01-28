package ru.kovalev.neoflexTest.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VacationPayCalculatorServiceImplTest {

    private final CountWorkDaysService countWorkDaysService =
            (start, finish) -> 5;

    private final VacationPayCalculatorServiceImpl service =
            new VacationPayCalculatorServiceImpl(countWorkDaysService);

    @Test
    void calculateByDays() {
        double result = service.calculateCompensation(1000, 5);
        assertEquals(5000, result);
    }
}
