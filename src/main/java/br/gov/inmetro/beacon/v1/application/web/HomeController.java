package br.gov.inmetro.beacon.v1.application.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class HomeController {

    @GetMapping("/")
    public ModelAndView root(){
        ModelAndView mv = new ModelAndView("redirect:/records");
        return mv;
    }

}
