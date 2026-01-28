package ru.kovalev.neoflexTest.services;

import java.time.LocalDate;

public interface CountWorkDaysService {
    int countWorkDays(LocalDate start, LocalDate finish);
}
