package br.gov.inmetro.beacon.domain;

import java.security.*;

public class BeaconSignatureRsa {

    private PublicKey pubKey;

    public PublicKey getPubKey() {
        return pubKey;
    }

    public void setPubKey(PublicKey pubKey) {
        this.pubKey = pubKey;
    }


    public byte[] geraAssinatura(String mensagem) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance("RSA");

        //Geração das chaves públicas e privadas
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        SecureRandom secRan = new SecureRandom();
        kpg.initialize(512, secRan);
        KeyPair keyP = kpg.generateKeyPair();
        this.pubKey = keyP.getPublic();
        PrivateKey priKey = keyP.getPrivate();

        //Inicializando Obj Signature com a Chave Privada
        sig.initSign(priKey);

        //Gerar assinatura
        sig.update(mensagem.getBytes());
        byte[] assinatura = sig.sign();

        return assinatura;
    }

}
