package backend.controller;

import backend.model.Sample;
import backend.model.User;
import backend.services.role.IRoleService;
import backend.services.sample.ISampleService;
import backend.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping(value = {"/"})
public class SampleController {
    @Autowired
    public ISampleService sampleService;
    @Autowired
    public IRoleService roleService;
    @Autowired
    public IUserService userService;

    @ModelAttribute("sampleList")
    public Iterable<Sample> sampleList() {
        return sampleService.findAll();
    }

    @ModelAttribute("registerUser")
    public User registerUser() {return new User();}

    @ModelAttribute("loginUser")
    public User loginUser() {return new User();}

    @GetMapping
    public ModelAndView showHomePage() {
        ModelAndView view = new ModelAndView("index");
        roleService.synchronizedRole();
        return view;
    }

    @GetMapping("*")
    public ModelAndView showError404() {
        return new ModelAndView("404/error.404");
    }

    @GetMapping("/register")
    public ModelAndView showFormRegister() {
        ModelAndView view = new ModelAndView("login-register/register");
//        view.addObject("validate", " Register Form");
        view.addObject("option", "register");
        return view;
    }

    @PostMapping("/register")
    public String actionRegister(
            @Validated
            @ModelAttribute("registerUser")
            User user, BindingResult bindingResult, RedirectAttributes rd) {
        boolean existUserName = userService.checkExistUserName(user.getUserName()).isPresent();
        boolean existEmail = userService.checkExistEmail(user.getEmail()).isPresent();
        boolean error = bindingResult.hasFieldErrors();
        if (existUserName || existEmail || error) {
            rd.addFlashAttribute("validate", "Register Failed!!!");
            return "redirect:/register";
        }
        userService.save(user);
        rd.addFlashAttribute("validate", "Register Success!!!");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public ModelAndView showFormLogin() {
        ModelAndView view = new ModelAndView("login-register/login");
        view.addObject("option", "login");
        return view;
    }

    @PostMapping("/login")
    public String actionLogin(
            @ModelAttribute("loginUser") User user, RedirectAttributes rd) {
        Optional<User> checkLogin = userService.loginUser(user.getUserName(), user.getPassword());
        User loginUser = null;
        if (checkLogin.isPresent()) {
            loginUser = checkLogin.get();
        }
        if (loginUser == null) {
            rd.addFlashAttribute("validate", "Login Failed!!!");
            return "redirect:/login";
        }
        rd.addFlashAttribute("validate", "Login Success!!!");
        return "redirect:/";
    }
}
