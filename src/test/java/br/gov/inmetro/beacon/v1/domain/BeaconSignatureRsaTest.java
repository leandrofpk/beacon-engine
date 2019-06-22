package br.gov.inmetro.beacon.v1.domain;

import br.gov.inmetro.beacon.v1.domain.BeaconSignatureRsa;
import org.junit.Test;

import java.security.*;

public class BeaconSignatureRsaTest {


    @Test
    public void teste() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        //Remetente Gera Assinatura Digital para uma Mensagem
        BeaconSignatureRsa beaconRsa = new BeaconSignatureRsa();
        String mensagem = "Exemplo de mensagem.";
        byte[] assinatura = beaconRsa.geraAssinatura(mensagem);
        //Guarda Chave Pública para ser Enviada ao Destinatário
        PublicKey pubKey = beaconRsa.getPubKey();


        System.out.println(assinatura);



    }



}