package br.gov.inmetro.beacon.domain;

import org.junit.Test;

import java.security.*;

import static org.junit.Assert.*;

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