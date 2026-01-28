package ru.kovalev.neoflexTest.models;

import java.time.LocalDate;

public class InputData {
    private Double avgSalary;
    private Integer vacationDaysCount;
    private LocalDate startDate;
    private LocalDate finishDate;

    public InputData() {
    }

    public InputData(Double avgSalary, Integer vacationDaysCount) {
        this.avgSalary = avgSalary;
        this.vacationDaysCount = vacationDaysCount;
    }

    public InputData(Double avgSalary, LocalDate startDate, LocalDate finishDate) {
        this.avgSalary = avgSalary;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }


    public Double getAvgSalary() {
        return avgSalary;
    }

    public void setAvgSalary(Double avgSalary) {
        this.avgSalary = avgSalary;
    }

    public Integer getVacationDaysCount() {
        return vacationDaysCount;
    }

    public void setVacationDaysCount(Integer vacationDaysCount) {
        this.vacationDaysCount = vacationDaysCount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }
}
