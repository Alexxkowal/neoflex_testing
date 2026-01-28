package ru.kovalev.neoflexTest.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class FileHolidayProvider implements HolidayProvider{
    private final String fileName;
    private final Set<LocalDate> holidays = new HashSet<>();

    public FileHolidayProvider(@Value("${holidays.file:holidays.txt}") String fileName) {
        this.fileName = fileName;
    }

    @PostConstruct
    public void init() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            if (is == null) throw new IOException("Файл не найден: " + fileName);

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    holidays.add(LocalDate.parse(line.trim()));
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Не удалось загрузить файл праздников: " + fileName, e
            );
        }
    }

    @Override
    public Set<LocalDate> loadHolidays() {
        return holidays;
    }
}
