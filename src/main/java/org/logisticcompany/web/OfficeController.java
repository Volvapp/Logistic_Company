package org.logisticcompany.web;

import org.logisticcompany.model.binding.OfficeAddBindingModel;
import org.logisticcompany.model.service.OfficeServiceModel;
import org.logisticcompany.service.LogisticCompanyService;
import org.logisticcompany.service.OfficeService;
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
@RequestMapping("/offices")
public class OfficeController {

    private final OfficeService officeService;

    private final ModelMapper modelMapper;

    private final LogisticCompanyService logisticCompanyService;

    public OfficeController(OfficeService officeService, ModelMapper modelMapper, LogisticCompanyService logisticCompanyService) {
        this.officeService = officeService;
        this.modelMapper = modelMapper;
        this.logisticCompanyService = logisticCompanyService;
    }

    @GetMapping("/add-office")
    public String addOffice(Model model) {

        model.addAttribute("companies", logisticCompanyService.findAllCompanies());

        return "add-office";
    }

    @PostMapping("/add-office")
    public String addOfficeConfirm(@Valid OfficeAddBindingModel officeAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("officeAddBindingModel", officeAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.officeAddBindingModel", bindingResult);

            return "redirect:/offices/add-office";
        }

        officeService.createOffice(modelMapper.map(officeAddBindingModel, OfficeServiceModel.class), principal.getName());

        return "redirect:/";
    }

    @ModelAttribute
    public OfficeAddBindingModel officeAddBindingModel() {
        return new OfficeAddBindingModel();
    }
}
