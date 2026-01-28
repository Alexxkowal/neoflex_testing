package ru.kovalev.neoflexTest.contorllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kovalev.neoflexTest.controllers.VacationPayCalculatorController;
import ru.kovalev.neoflexTest.services.CountWorkDaysService;
import ru.kovalev.neoflexTest.services.VacationPayCalculatorServiceImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VacationPayCalculatorControllerTest {

    private VacationPayCalculatorController controller;
    private VacationPayCalculatorServiceImpl service;

    @BeforeEach
    void setUp() {
        CountWorkDaysService mockCountService = new CountWorkDaysService() {
            @Override
            public int countWorkDays(LocalDate start, LocalDate finish) {
                return 5;
            }
        };
        service = new VacationPayCalculatorServiceImpl(mockCountService);
        controller = new VacationPayCalculatorController(service);
    }

    @Test
    void testFinishBeforeStartThrows() {
        LocalDate start = LocalDate.of(2026, 1, 10);
        LocalDate finish = LocalDate.of(2026, 1, 5);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> controller.calculate(1000.0, null, start, finish)
        );
        assertEquals("Дата окончания не может быть раньше даты начала", ex.getMessage());
    }

    @Test
    void testOnlyStartOrFinishThrows() {
        LocalDate start = LocalDate.of(2026, 1, 5);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> controller.calculate(1000.0, null, start, null)
        );
        assertEquals("Нужно указать и дату начала, и дату окончания", ex.getMessage());
    }

    @Test
    void testBothDatesAndDaysThrows() {
        LocalDate start = LocalDate.of(2026, 1, 5);
        LocalDate finish = LocalDate.of(2026, 1, 10);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> controller.calculate(1000.0, 5, start, finish)
        );
        assertEquals("Используйте либо количество дней, либо даты", ex.getMessage());
    }

    @Test
    void testValidDateRangePasses() {
        LocalDate start = LocalDate.of(2026, 1, 5);
        LocalDate finish = LocalDate.of(2026, 1, 10);
        double result = controller.calculate(100.0, null, start, finish);
        assertEquals(500.0, result);
    }

    @Test
    void testValidVacationDaysPasses() {
        double result = controller.calculate(100.0, 5, null, null);
        assertEquals(500.0, result);
    }
}
