package br.gov.inmetro.beacon.api;

import br.gov.inmetro.beacon.core.dominio.repositorio.Records;
import br.gov.inmetro.beacon.core.service.CadastraRegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/rest/record", produces=MediaType.APPLICATION_JSON_VALUE)
public class RecordsPostResource {

    private CadastraRegistroService cadastraRegistroService;

    @Autowired
    public RecordsPostResource(CadastraRegistroService cadastraRegistroService, Records registros) {
        this.cadastraRegistroService = cadastraRegistroService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public  ResponseEntity<?> novo(@Valid @RequestBody RecordDto record){

        cadastraRegistroService.novoRegistro(record);

        return new ResponseEntity<RecordDto>(record, HttpStatus.OK); //funcionando

    }


    //    @RequestMapping("/teste")
//    public void novo(){
//        String numero = "01110110100001000011011001010011100101010001011001000001000101101000000001011000100001110000010101110011011001000100011000010111110010100101100001111010001101100111011000101001101110000100010101111011001110101000011110110111010011001111110011001101111111001100100110111101100101100100001001110100011011001100111110010111010110011011100010100100101011101100110010111010100101010111100010010011100111000110011110000011011011000110101110100110111001110110010101000010010110010101100010000110010000100110011000100001";
//        String assinatura = "iQEzBAABCAAdFiEEadf0EH6PW9qPd1jGsvk8CRGvunoFAlp4RmcACgkQsvk8CRGvunpSaQf/b8GjFhSQjl2+yrD4da/08JnQ9bp+7yLPaQHyqJkNx3FKTSSr9tckGOsTMG1WyXQStasIIfnmmjnvItkNAY5r9JsEIlo9rNJod37Ek9UCwma0EUcd7IOO3/+2XEQ6mZOeCFeqo4tG6rWJ30IkPJ96nAEq8dYk457SBITJd4tOfe8cD3lgHQ83ni7Pi2IhztlMKJsk1De2uSqCaJCDSCNbwI+ycwO8TgbRJBNtDBoS6s6qyCdoSf7drwINF+ZuQ785wVzh0bAr0TbF2a+RTAdJpAGyd6IGID2zgK2w+awgdZL5OKlRacuTztGVkxqRfDjCHVAfX25pu2BFXJlkAuZR2w===yjjp";
//
//        br.gov.inmetro.beacon.core.dominio.Record r = new br.gov.inmetro.beacon.core.dominio.Record(numero, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), assinatura);
//        cadastraRegistroService.novoRegistro(r);
//    }

}
