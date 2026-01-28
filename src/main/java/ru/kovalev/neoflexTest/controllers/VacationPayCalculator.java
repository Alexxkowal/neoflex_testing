package ru.kovalev.neoflexTest.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kovalev.neoflexTest.models.InputData;
import ru.kovalev.neoflexTest.services.VacationPayCalculatorServiceImpl;
import ru.kovalev.neoflexTest.utils.InputDataValidator;

@Controller
@RequestMapping("/calculate")
public class VacationPayCalculator {
    private final VacationPayCalculatorServiceImpl vacationPayCalculatorService;
    private final InputDataValidator validator;

    @Autowired
    public VacationPayCalculator(VacationPayCalculatorServiceImpl vacationPayCalculatorService, InputDataValidator validator) {
        this.vacationPayCalculatorService = vacationPayCalculatorService;
        this.validator = validator;
    }

    @GetMapping
    public String showForm(Model model){
        model.addAttribute("inputData", new InputData());
        return "mainPage";
    }

    @PostMapping
    public String calculate(@ModelAttribute("inputData") InputData inputData, Model model, BindingResult bindingResult){
        validator.validate(inputData, bindingResult);
        if (bindingResult.hasErrors()){
            return "mainPage";
        }
        double result = 0;
        if (inputData.getStartDate() != null && inputData.getFinishDate()!=null){
            result = vacationPayCalculatorService.calculateCompensation(inputData.getAvgSalary(),
                    inputData.getStartDate(),
                    inputData.getFinishDate());
        }
        else{
            result = vacationPayCalculatorService.calculateCompensation(inputData.getAvgSalary(),
                    inputData.getVacationDaysCount());
        }
        model.addAttribute("result", result);
        return "mainPage";
    }
}
