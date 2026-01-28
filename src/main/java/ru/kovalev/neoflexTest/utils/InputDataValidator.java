package ru.kovalev.neoflexTest.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kovalev.neoflexTest.models.InputData;

@Component
public class InputDataValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return InputData.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        InputData inputData = (InputData) target;

        if (inputData.getAvgSalary() == null) {
            errors.rejectValue("avgSalary", "", "Средняя зарплата обязательна для заполнения!");
        } else if (inputData.getAvgSalary() <= 0) {
            errors.rejectValue("avgSalary", "", "Зарплата должна быть больше 0!");
        }

        Integer days = inputData.getVacationDaysCount();
        boolean daysMissing = (days == null);
        boolean datesMissing = (inputData.getStartDate() == null || inputData.getFinishDate() == null);

        if (daysMissing && datesMissing) {
            errors.rejectValue("vacationDaysCount", "", "Введите количество дней ИЛИ выберите даты отпуска");
            errors.rejectValue("startDate", "", "Необходимо заполнить даты, если не указаны дни");
        }

        if (!daysMissing && days <= 0) {
            errors.rejectValue("vacationDaysCount", "", "Дней отпуска должно быть больше 0!");
        }

        if (!datesMissing) {
            if (inputData.getStartDate().isAfter(inputData.getFinishDate())) {
                errors.rejectValue("startDate", "", "Дата начала не может быть позже даты окончания");
            }
        }
    }
}