package br.gov.inmetro.beacon;

import br.gov.inmetro.beacon.core.dominio.Registro;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDateTime;

public class RegistroTest {

    @Test
    public void validarFormatoDaData(){
        Registro registro = new Registro("1", "19/05/2018 23:27","uma assinatura");
        registro.toString();
    }

    @Test
    public void validarFormatoDaData2(){
        Registro registro = new Registro("1", LocalDateTime.now(),"uma assinatura");
        registro.toString();
    }

}
