package br.gov.inmetro.beacon.api;

import br.gov.inmetro.beacon.core.dominio.repositorio.Registros;
import br.gov.inmetro.beacon.core.infra.Registro;
import br.gov.inmetro.beacon.core.service.CadastraRegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/api/registros")
public class RegistrosResource {

    private CadastraRegistroService cadastraRegistroService;

    private Registros registros;

    @Autowired
    public RegistrosResource(CadastraRegistroService cadastraRegistroService, Registros registros) {
        this.cadastraRegistroService = cadastraRegistroService;
        this.registros = registros;
    }

//    @RequestMapping("/teste")
//    public void novo(){
//        String numero = "01110110100001000011011001010011100101010001011001000001000101101000000001011000100001110000010101110011011001000100011000010111110010100101100001111010001101100111011000101001101110000100010101111011001110101000011110110111010011001111110011001101111111001100100110111101100101100100001001110100011011001100111110010111010110011011100010100100101011101100110010111010100101010111100010010011100111000110011110000011011011000110101110100110111001110110010101000010010110010101100010000110010000100110011000100001";
//        String assinatura = "iQEzBAABCAAdFiEEadf0EH6PW9qPd1jGsvk8CRGvunoFAlp4RmcACgkQsvk8CRGvunpSaQf/b8GjFhSQjl2+yrD4da/08JnQ9bp+7yLPaQHyqJkNx3FKTSSr9tckGOsTMG1WyXQStasIIfnmmjnvItkNAY5r9JsEIlo9rNJod37Ek9UCwma0EUcd7IOO3/+2XEQ6mZOeCFeqo4tG6rWJ30IkPJ96nAEq8dYk457SBITJd4tOfe8cD3lgHQ83ni7Pi2IhztlMKJsk1De2uSqCaJCDSCNbwI+ycwO8TgbRJBNtDBoS6s6qyCdoSf7drwINF+ZuQ785wVzh0bAr0TbF2a+RTAdJpAGyd6IGID2zgK2w+awgdZL5OKlRacuTztGVkxqRfDjCHVAfX25pu2BFXJlkAuZR2w===yjjp";
//
//        br.gov.inmetro.beacon.core.dominio.Registro r = new br.gov.inmetro.beacon.core.dominio.Registro(numero, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), assinatura);
//        cadastraRegistroService.novoRegistro(r);
//    }

    @RequestMapping("/{data}")
    public Registro dataFormatoLong(@PathVariable String data){
        return registros.findByHora(longToLocalDateTime(data));
    }

    private LocalDateTime longToLocalDateTime(String data){
        long millis = new Long(data);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
        return localDateTime;
    }

    @RequestMapping("/ultimo")
    public Registro ultimo(){
        return registros.ultimo();
    }

    @RequestMapping("/primeiro")
    public Registro primeiro(){
        return registros.primeiro();
    }

    @RequestMapping("/proximo/{data}")
    public Registro proximo(@PathVariable String data){
        return registros.findByHora(longToLocalDateTime(data).plus(1, ChronoUnit.MINUTES));
    }

    @RequestMapping("/anterior/{data}")
    public Registro anterior(@PathVariable String data){
        return registros.findByHora(longToLocalDateTime(data).minus(1, ChronoUnit.MINUTES));
    }

}
