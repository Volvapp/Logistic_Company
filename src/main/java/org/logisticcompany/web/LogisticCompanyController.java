package org.logisticcompany.web;

import org.logisticcompany.model.binding.LogisticCompanyAddBindingModel;
import org.logisticcompany.model.service.LogisticCompanyServiceModel;
import org.logisticcompany.model.service.PackageServiceModel;
import org.logisticcompany.service.LogisticCompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/companies")
public class LogisticCompanyController {

    private final LogisticCompanyService logisticCompanyService;

    private final ModelMapper modelMapper;

    public LogisticCompanyController(LogisticCompanyService logisticCompanyService, ModelMapper modelMapper) {
        this.logisticCompanyService = logisticCompanyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add-company")
    public String addCompany() {
        return "add-company";
    }

    @PostMapping("/add-company")
    public String addCompanyConfirm(@Valid LogisticCompanyAddBindingModel logisticCompanyAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("logisticCompanyAddBindingModel", logisticCompanyAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.logisticCompanyAddBindingModel", bindingResult);

            return "redirect:/companies/add-company";
        }

        logisticCompanyService.createCompany(modelMapper.map(logisticCompanyAddBindingModel, LogisticCompanyServiceModel.class), principal.getName());

        return "redirect:/admin/my-packages";
    }

    @ModelAttribute
    public LogisticCompanyAddBindingModel logisticCompanyAddBindingModel() {
        return new LogisticCompanyAddBindingModel();
    }
}
