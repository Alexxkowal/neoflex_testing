package ru.kovalev.neoflexTest.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CountWorkDaysServiceImplTest {

    @Autowired
    CountWorkDaysService countWorkDaysService;

    @Test
    void countWorkDays_correctly() {
        int result = countWorkDaysService.countWorkDays(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 31)
        );
        assertEquals(15, result);
    }
}
