package ru.kovalev.neoflexTest.services;

import java.time.LocalDate;

public interface VacationPayCalculatorService {
     double calculateCompensation(double avgSalary, int vacationDaysCount);

     double calculateCompensation(double avgSalary, LocalDate startDate, LocalDate finishDate);
}
