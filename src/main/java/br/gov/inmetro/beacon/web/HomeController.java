package br.gov.inmetro.beacon.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView root(){
        ModelAndView mv = new ModelAndView("redirect:/records");
        return mv;
    }

}
