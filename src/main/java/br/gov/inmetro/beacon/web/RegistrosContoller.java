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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/registros")
public class RegistrosContoller {

    private Records registros;

    @Autowired
    public RegistrosContoller(Records registros) {
        this.registros = registros;
    }

    @GetMapping
    public ModelAndView pesquisar(RegistroFilter registroFilter, BindingResult result
            , Pageable pageable, HttpServletRequest httpServletRequest) {

        ModelAndView mv = new ModelAndView("registros/index");
        mv.addObject("registros", registros.obterTodos());
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView ver(@PathVariable("id") Record registro) {
        ModelAndView mv = new ModelAndView("registros/show");
        mv.addObject(registro);
        return mv;
    }

}
