package org.logisticcompany.web;

import org.logisticcompany.model.binding.EmployeeAddBindingModel;
import org.logisticcompany.model.service.EmployeeServiceModel;
import org.logisticcompany.service.LogisticCompanyService;
import org.logisticcompany.service.PackageService;
import org.logisticcompany.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final LogisticCompanyService logisticCompanyService;

    private final PackageService packageService;

    public EmployeeController(UserService userService, ModelMapper modelMapper, LogisticCompanyService logisticCompanyService, PackageService packageService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.logisticCompanyService = logisticCompanyService;
        this.packageService = packageService;
    }

    @GetMapping("/office-employee/packages")
    public String officeEmployeePackages(Model model){

        model.addAttribute("employeePackagesDetails", packageService.findAllEmployeePackagesDetails());

        return "employee-packages";
    }

    @GetMapping("/courier-employee/packages")
    public String courierEmployeePackages(Model model){

        model.addAttribute("courierPackagesDetails", packageService.findAllEmployeePackagesDetails());

        return "courier-packages";
    }

    @PostMapping("/deliver-package/{id}")
    public String deliverPackageConfirm(@PathVariable Long id, RedirectAttributes redirectAttributes, Principal principal) {

        packageService.deliverPackage(id, principal.getName());

        return "redirect:/";
    }

    @PostMapping("/accept-package/{id}")
    public String acceptPackageConfirm(@PathVariable Long id, RedirectAttributes redirectAttributes, Principal principal) {

        packageService.acceptPackage(id, principal.getName());

        return "redirect:/";
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
