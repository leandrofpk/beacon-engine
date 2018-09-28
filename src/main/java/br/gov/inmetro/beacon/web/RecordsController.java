package br.gov.inmetro.beacon.web;

import br.gov.inmetro.beacon.core.dominio.repositorio.Records;
import br.gov.inmetro.beacon.core.infra.Record;
import br.gov.inmetro.beacon.core.infra.RegistroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.core.env.Environment;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestScope
@RequestMapping("/records")
public class RecordsController {

    private Records records;

    private Environment env;

    @Autowired
    public RecordsController(Records records, Environment env) {
        this.records = records;
        this.env = env;
    }

    @GetMapping
    public ModelAndView pesquisar(RegistroFilter registroFilter, BindingResult result
            , Pageable pageable, HttpServletRequest httpServletRequest) {

        ModelAndView mv = new ModelAndView("records/index");
        mv.addObject("records", records.obterTodos());
        mv.addObject("url", env.getProperty("beacon.url"));
        mv.addObject("lastRecord", records.last());
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView ver(@PathVariable("id") Record record) {
        ModelAndView mv = new ModelAndView("records/show");
        mv.addObject(record);
        return mv;
    }

}
