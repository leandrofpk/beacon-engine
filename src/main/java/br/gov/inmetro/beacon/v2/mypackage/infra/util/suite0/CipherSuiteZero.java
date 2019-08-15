package br.gov.inmetro.beacon.v2.mypackage.infra.util.suite0;

import br.gov.inmetro.beacon.v2.mypackage.infra.util.ICipherSuite;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class CipherSuiteZero implements ICipherSuite {



    public String getDigest(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] encodedhash = digest.digest(input.getBytes(UTF_8));
            return Hex.toHexString(encodedhash).toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getDigest(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] encodedhash = digest.digest(input);
            return Hex.toHexString(encodedhash).toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String signBytes15(String plainText, Key privKey) {
        byte[] cipherText = null;

        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            byte[] input = plainText.getBytes();
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, privKey);
            cipherText = cipher.doFinal(input);

        } catch (Exception e){
            e.printStackTrace();
        }

        return Hex.toHexString(cipherText).toUpperCase();
    }

    public String verifySignBytes15(String cipherText, PublicKey pubKey) {
        byte [] plainText = null;
        try {

            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

//            byte[] input = cipherText.getBytes();
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");

            cipher.init(Cipher.DECRYPT_MODE, pubKey);
            plainText = cipher.doFinal(cipherText.getBytes(UTF_8));



        } catch (Exception e){
            e.printStackTrace();
        }

        return Hex.toHexString(plainText).toUpperCase();
    }


}