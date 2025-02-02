package org.logisticcompany.web;

import org.logisticcompany.model.binding.PackageAddBindingModel;
import org.logisticcompany.model.service.PackageServiceModel;
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

    public PackageController(PackageService packageService, UserService userService, ModelMapper modelMapper) {
        this.packageService = packageService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/my-packages")
    public String myPackages(Model model) {

        //model.addAttribute("packagesDetails", packageService.findAll());

        if (!model.containsAttribute("canUserBuy")) {
            model.addAttribute("canUserBuy", true);
        }

        return "my-packages";
    }

    @PostMapping("/buy-package/{id}")
    public String buyComputerConfirm(@PathVariable Long id, RedirectAttributes redirectAttributes, Principal principal) {

//        boolean canUserPayPackage = userService.canUserPayPackage(principal.getName(), id);
//
//        if (!canUserPayPackage) {
//            redirectAttributes.addFlashAttribute("canUserBuy", false);
//
//            return "redirect:/packages/my-packages";
//        }
//
//        userService.payPackage(principal.getName(), id);

        return "redirect:/packages/my-packages";
    }

    @GetMapping("/add-package")
    public String add() {
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
