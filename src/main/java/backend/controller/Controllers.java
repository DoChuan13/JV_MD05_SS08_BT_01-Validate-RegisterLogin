package backend.controller;

import backend.services.IGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"/"})
public class Controllers {
    @Autowired
    public IGenericService iGenericService;

    @GetMapping
    public ModelAndView showHomePage() {
        ModelAndView view = new ModelAndView("index");
        return view;
    }
}
