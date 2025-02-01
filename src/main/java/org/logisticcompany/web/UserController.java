    package org.logisticcompany.web;

    import org.logisticcompany.model.binding.UserRegisterBindingModel;
    import org.logisticcompany.model.service.UserServiceModel;
    import org.logisticcompany.service.Impl.UserServiceImpl;
    import org.modelmapper.ModelMapper;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.ModelAttribute;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;

    import javax.validation.Valid;

    @Controller
    @RequestMapping("/users")
    public class UserController {

        private final UserServiceImpl userService;

        private final ModelMapper modelMapper;

        public UserController(UserServiceImpl userService, ModelMapper modelMapper) {
            this.userService = userService;
            this.modelMapper = modelMapper;
        }

        @GetMapping("/login")
        public String login(){
            return "login";
        }

        @PostMapping("/login-error")
        public String loginFailed(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username, RedirectAttributes redirectAttributes){

            redirectAttributes.addFlashAttribute("username",username);
            redirectAttributes.addFlashAttribute("bad_credentials", true);

            return "redirect:/users/login";
        }

        @GetMapping("/register")
        public String register(Model model){

            if(!model.containsAttribute("isUserOrEmailOccupied")){
                model.addAttribute("isUserOrEmailOccupied", false);
            }

            return "register";
        }

        @PostMapping("/register")
        public String registerConfirm(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes){

            if(bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())){

                redirectAttributes.addFlashAttribute("userRegisterBindingModel",userRegisterBindingModel);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",bindingResult);

                return "redirect:register";
            }

            boolean isUserExistingByEmailOrUsername = userService.isUserExistingByEmailOrUsername(userRegisterBindingModel.getEmail(),userRegisterBindingModel.getUsername());

            if(isUserExistingByEmailOrUsername){
                redirectAttributes.addFlashAttribute("userRegisterBindingModel",userRegisterBindingModel);
                redirectAttributes.addFlashAttribute("isUserOrEmailOccupied", true);

                return "redirect:register";
            }

            userService.registerUser(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

            return "redirect:/";
        }

        @ModelAttribute
        public UserRegisterBindingModel userRegisterBindingModel(){
            return new UserRegisterBindingModel();
        }
    }
