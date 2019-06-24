package br.gov.inmetro.beacon.v1.application.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class DownloadController {

    private final Environment environment;

    @Autowired
    public DownloadController(Environment environment) {
        this.environment = environment;
    }

    @RequestMapping(value = "/certificate/inmetro.crt", method = RequestMethod.GET)
    public HttpEntity<byte[]> download() throws IOException {

        String certificatePath = environment.getProperty("beacon.x509.certificate");

        byte[] arquivo = Files.readAllBytes( Paths.get(certificatePath) );
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=\"inmetro.crt\"");
        return new HttpEntity<>( arquivo, httpHeaders);
    }


}
