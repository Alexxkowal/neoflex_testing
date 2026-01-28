package ru.kovalev.neoflexTest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VacationPayCalculatorServiceImpl implements VacationPayCalculatorService{
    private final CountWorkDaysService countWorkDaysService;

    @Autowired
    public VacationPayCalculatorServiceImpl(CountWorkDaysService countWorkDaysService) {
        this.countWorkDaysService = countWorkDaysService;
    }

    @Override
    public double calculateCompensation(double avgSalary, int vacationDaysCount) {
        return avgSalary * vacationDaysCount;
    }

    @Override
    public double calculateCompensation(double avgSalary, LocalDate startDate, LocalDate finishDate) {
        long workDays = countWorkDaysService.countWorkDays(startDate, finishDate);
        return avgSalary * workDays;
    }
}
