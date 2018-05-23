package br.gov.inmetro.beacon.web;

import br.gov.inmetro.beacon.core.dominio.repositorio.Registros;
import br.gov.inmetro.beacon.core.infra.PageWrapper;
import br.gov.inmetro.beacon.core.infra.Registro;
import br.gov.inmetro.beacon.core.infra.RegistroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    private Registros registros;

    @Autowired
    public RegistrosContoller(Registros registros) {
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
    public ModelAndView ver(@PathVariable("id") Registro registro) {
        ModelAndView mv = new ModelAndView("registros/show");

        mv.addObject(registro);
        return mv;
    }

}
