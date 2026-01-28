package ru.kovalev.neoflexTest.controllers;


import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kovalev.neoflexTest.services.VacationPayCalculatorServiceImpl;

import java.time.LocalDate;

@RestController
@Validated
public class VacationPayCalculatorController {
    private final VacationPayCalculatorServiceImpl vacationPayCalculatorService;

    @Autowired
    public VacationPayCalculatorController(VacationPayCalculatorServiceImpl vacationPayCalculatorService) {
        this.vacationPayCalculatorService = vacationPayCalculatorService;
    }


    @GetMapping("/calculate")
    public double calculate(
            @RequestParam("avgSalary") @Positive(message = "Средняя зарплата должна быть положительной") double avgSalary,
            @RequestParam(value = "vacationDaysCount", required = false) @Positive(message = "Количество дней должно быть положительным") Integer vacationDaysCount,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "finishDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishDate
    ) {
        validateInput(vacationDaysCount, startDate, finishDate);
        if (startDate != null) {
            return vacationPayCalculatorService
                    .calculateCompensation(avgSalary, startDate, finishDate);
        }
        return vacationPayCalculatorService
                .calculateCompensation(avgSalary, vacationDaysCount);
    }

    private void validateInput(
            Integer vacationDaysCount,
            LocalDate startDate,
            LocalDate finishDate
    ) {
        if (vacationDaysCount != null && (startDate != null || finishDate != null)) {
            throw new IllegalArgumentException("Используйте либо количество дней, либо даты");
        }
        if (startDate != null || finishDate != null) {
            if (startDate == null || finishDate == null) {
                throw new IllegalArgumentException("Нужно указать и дату начала, и дату окончания");
            }
            if (finishDate.isBefore(startDate)) {
                throw new IllegalArgumentException("Дата окончания не может быть раньше даты начала");
            }
        }
        if (vacationDaysCount == null && startDate == null) {
            throw new IllegalArgumentException("Нужно указать количество дней или период отпуска");
        }
    }
}
