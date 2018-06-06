package br.gov.inmetro.beacon;

import br.gov.inmetro.beacon.core.dominio.RegistroDto;
import org.junit.Test;

import java.time.LocalDateTime;

public class RegistroTest {

    @Test
    public void validarFormatoDaData(){
        RegistroDto registro = new RegistroDto("1", "19/05/2018 23:27","uma assinatura");
        registro.toString();
    }

    @Test
    public void validarFormatoDaData2(){
        RegistroDto registro = new RegistroDto("1", LocalDateTime.now(),"uma assinatura");
        registro.toString();
    }

}
