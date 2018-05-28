package br.gov.inmetro.beacon.core.service;

import br.gov.inmetro.beacon.core.dominio.OriginEnum;
import br.gov.inmetro.beacon.core.dominio.Registro;
import br.gov.inmetro.beacon.core.dominio.repositorio.Records;
import br.gov.inmetro.beacon.core.infra.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastraRegistroService {

    private Records records;

    @Autowired
    public CadastraRegistroService(
            Records records) {
        this.records = records;
    }

    public void novoRegistro(Registro registro){
        Record registroBd = new Record();
        registroBd.setOutputValue(registro.getNumero());
        registroBd.setSignature(registro.getAssinatura());
        registroBd.setTime(registro.getHora());
        registroBd.setOrigin(OriginEnum.AUTO);

        records.save(registroBd);
    }

}
