package org.logisticcompany.web;

import org.logisticcompany.model.binding.PackageAddBindingModel;
import org.logisticcompany.model.service.PackageServiceModel;
import org.logisticcompany.service.OfficeService;
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
@RequestMapping("/packages")
public class PackageController {

    private final PackageService packageService;

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final OfficeService officeService;

    public PackageController(PackageService packageService, UserService userService, ModelMapper modelMapper, OfficeService officeService) {
        this.packageService = packageService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.officeService = officeService;
    }

    @GetMapping("/my-packages")
    public String myPackages(Model model, Principal principal) {

        model.addAttribute("clientPackagesDetails", packageService.findAllClientPackagesDetails(principal.getName()));

        if (!model.containsAttribute("canUserPay")) {
            model.addAttribute("canUserPay", true);
        }

        return "my-packages";
    }

    @PostMapping("/pay-package/{id}")
    public String payPackageConfirm(@PathVariable Long id, RedirectAttributes redirectAttributes, Principal principal) {

        boolean canUserPayPackage = userService.pay(principal.getName(), id);

        if (!canUserPayPackage) {
            redirectAttributes.addFlashAttribute("canUserPay", false);

            return "redirect:/packages/my-packages";
        }

        return "redirect:/";
    }

    @GetMapping("/add-package")
    public String add(Model model, Principal principal) {

        model.addAttribute("officeAddresses", officeService.findAddresses(principal.getName()));

        return "add-package";
    }

    @PostMapping("/add-package")
    public String addConfirm(@Valid PackageAddBindingModel packageAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("packageAddBindingModel", packageAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.packageAddBindingModel", bindingResult);

            return "redirect:/packages/add-package";
        }

        packageService.createPackage(modelMapper.map(packageAddBindingModel, PackageServiceModel.class), principal.getName());

        return "redirect:/packages/my-packages";
    }

    @ModelAttribute
    public PackageAddBindingModel packageAddBindingModel() {
        return new PackageAddBindingModel();
    }

}
