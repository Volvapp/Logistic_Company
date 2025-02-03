package org.logisticcompany.web;

import org.logisticcompany.model.LogisticCompany;
import org.logisticcompany.model.binding.EmployeeAddBindingModel;
import org.logisticcompany.model.service.EmployeeServiceModel;
import org.logisticcompany.service.LogisticCompanyService;
import org.logisticcompany.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final LogisticCompanyService logisticCompanyService;

    public EmployeeController(UserService userService, ModelMapper modelMapper, LogisticCompanyService logisticCompanyService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.logisticCompanyService = logisticCompanyService;
    }

    @GetMapping("/add-employee")
    public String addEmployee(Model model) {

        if (!model.containsAttribute("isUserOrEmailOccupied")) {
            model.addAttribute("isUserOrEmailOccupied", false);
        }

        if (!model.containsAttribute("companies")) {
            model.addAttribute("companies", logisticCompanyService.findAllCompanies());
        }

        return "add-employee";
    }

    @PostMapping("/add-employee")
    public String addEmployeeConfirm(@Valid EmployeeAddBindingModel employeeAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("employeeAddBindingModel", employeeAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.employeeAddBindingModel", bindingResult);

            return "redirect:/employees/add-employee";
        }

        boolean isUserExistingByEmailOrUsername = userService.isUserExistingByEmailOrUsername(employeeAddBindingModel.getEmail(),employeeAddBindingModel.getUsername());

        if(isUserExistingByEmailOrUsername){
            redirectAttributes.addFlashAttribute("employeeAddBindingModel",employeeAddBindingModel);
            redirectAttributes.addFlashAttribute("isUserOrEmailOccupied", true);

            return "redirect:/employees/add-employee";
        }

        userService.createEmployee(modelMapper.map(employeeAddBindingModel, EmployeeServiceModel.class), principal.getName());

        return "redirect:/";
    }

    @ModelAttribute
    public EmployeeAddBindingModel employeeAddBindingModel() {
        return new EmployeeAddBindingModel();
    }
}
