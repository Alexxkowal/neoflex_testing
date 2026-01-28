package ru.kovalev.neoflexTest.services;

import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class CountWorkDaysServiceImpl implements CountWorkDaysService {
    private final HolidayProvider holidayProvider;

    public CountWorkDaysServiceImpl(HolidayProvider holidayProvider) {
        this.holidayProvider = holidayProvider;
    }

    @Override
    public int countWorkDays(LocalDate start, LocalDate finish) {
        Set<LocalDate> holidays = holidayProvider.loadHolidays();
        int count = 0;
        LocalDate current = start;

        while (!current.isAfter(finish)) {
            boolean isWeekend = (current.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    current.getDayOfWeek() == DayOfWeek.SUNDAY);
            boolean isHoliday = holidays.contains(current);
            if (!isHoliday && !isWeekend) {
                count++;
            }
            current = current.plusDays(1);
        }
        return count;
    }
}
