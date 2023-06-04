package backend.controller;

import backend.model.Sample;
import backend.services.role.IRoleService;
import backend.services.sample.ISampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"/"})
public class SampleController {
    @Autowired
    public ISampleService sampleService;
    @Autowired
    public IRoleService roleService;

    @ModelAttribute("sampleList")
    public Iterable<Sample> sampleList() {
        return sampleService.findAll();
    }

    @GetMapping
    public ModelAndView showHomePage() {
        ModelAndView view = new ModelAndView("index");
        return view;
    }
}
