package br.gov.inmetro.beacon.web;

import br.gov.inmetro.beacon.core.dominio.repositorio.Registros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registros")
public class RegistrosContoller {

    private Registros registros;

    @Autowired
    public RegistrosContoller(Registros registros) {
        this.registros = registros;
    }



}
