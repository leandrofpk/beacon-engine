package br.gov.inmetro.beacon.v1.application.web;

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

    @RequestMapping(value = "/certificate/publickey.pem", method = RequestMethod.GET)
    public HttpEntity<byte[]> download() throws IOException {

        byte[] arquivo = Files.readAllBytes( Paths.get("publickey.pem") );

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Disposition", "attachment;filename=\"publickey.pem\"");

//        HttpEntity<byte[]> entity = new HttpEntity<>( arquivo, httpHeaders);

        return new HttpEntity<>( arquivo, httpHeaders);
    }


}
