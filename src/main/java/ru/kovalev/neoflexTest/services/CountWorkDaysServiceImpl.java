package ru.kovalev.neoflexTest.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class CountWorkDaysServiceImpl implements CountWorkDaysService {
    private final Set<LocalDate> holidays = new HashSet<>();

    @PostConstruct
    public void init() {
        try (InputStream is = getClass().getResourceAsStream("/holidays.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    try {
                        holidays.add(LocalDate.parse(line.trim()));
                    } catch (Exception e) {
                        System.err.println("Пропущена некорректная дата: " + line);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки праздников: " + e.getMessage());
        }
    }
    @Override
    public int countWorkDays(LocalDate start, LocalDate finish) {
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
