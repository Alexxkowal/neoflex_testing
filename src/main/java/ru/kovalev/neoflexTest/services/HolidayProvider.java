package ru.kovalev.neoflexTest.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public interface HolidayProvider {
    Set<LocalDate> loadHolidays();
}
