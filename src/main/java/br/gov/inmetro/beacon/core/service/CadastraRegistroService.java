package br.gov.inmetro.beacon.core.service;

import br.gov.inmetro.beacon.core.dominio.OrigemEnum;
import br.gov.inmetro.beacon.core.dominio.Registro;
import br.gov.inmetro.beacon.core.dominio.repositorio.Registros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastraRegistroService {

    private Registros registros;

    @Autowired
    public CadastraRegistroService(Registros registros) {
        this.registros = registros;
    }

    public void novoRegistro(Registro registro){

//        registros.find

        br.gov.inmetro.beacon.core.infra.Registro registroBd = new br.gov.inmetro.beacon.core.infra.Registro();
        registroBd.setNumero(registro.getNumero());
        registroBd.setAssinatura(registro.getAssinatura());
        registroBd.setHora(registro.getHora());
        registroBd.setOrigem(OrigemEnum.BEACON);

        registros.save(registroBd);
    }

}
