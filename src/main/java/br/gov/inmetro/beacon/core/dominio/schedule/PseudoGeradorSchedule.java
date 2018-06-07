package br.gov.inmetro.beacon.core.dominio.schedule;

import br.gov.inmetro.beacon.core.dominio.RegistroDto;
import br.gov.inmetro.beacon.core.service.CadastraRegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Component
@EnableScheduling
public class PseudoGeradorSchedule {

    private CadastraRegistroService service;

    @Autowired
    public PseudoGeradorSchedule(CadastraRegistroService service) {
        this.service = service;
    }

    @Scheduled(cron = "*/60 * * * * *")
    public void novoRegistro() {
        gerarRegistro();
    }

    private void gerarRegistro(){
        String assinatura = "iQEzBAABCAAdFiEEadf0EH6PW9qPd1jGsvk8CRGvunoFAlp4RmcACgkQsvk8CRGvunpSaQf/b8GjFhSQjl2+yrD4da/08JnQ9bp+7yLPaQHyqJkNx3FKTSSr9tckGOsTMG1WyXQStasIIfnmmjnvItkNAY5r9JsEIlo9rNJod37Ek9UCwma0EUcd7IOO3/+2XEQ6mZOeCFeqo4tG6rWJ30IkPJ96nAEq8dYk457SBITJd4tOfe8cD3lgHQ83ni7Pi2IhztlMKJsk1De2uSqCaJCDSCNbwI+ycwO8TgbRJBNtDBoS6s6qyCdoSf7drwINF+ZuQ785wVzh0bAr0TbF2a+RTAdJpAGyd6IGID2zgK2w+awgdZL5OKlRacuTztGVkxqRfDjCHVAfX25pu2BFXJlkAuZR2w===yjjp";
        RegistroDto r = new RegistroDto(gerarNumero512(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), assinatura);
        System.out.println(r);
//        service.novoRegistro(r);
    }

    private String gerarNumero512(){
        Random gerador = new Random();
        String string512 = "";
        for (int i = 0; i < 512; i++) {
            string512 = string512 + gerador.nextInt(2);
         }
        return string512;
    }


}
