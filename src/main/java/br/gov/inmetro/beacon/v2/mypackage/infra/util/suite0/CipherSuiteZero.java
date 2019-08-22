package br.gov.inmetro.beacon.v2.mypackage.infra.util.suite0;

import br.gov.inmetro.beacon.v2.mypackage.infra.util.ICipherSuite;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.*;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
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

    @Override
    public String signBytes15(String plainText, PrivateKey privateKey) throws Exception {

            Signature privateSignature = Signature.getInstance("NONEwithRSA");
            privateSignature.initSign(privateKey);
            privateSignature.update(plainText.getBytes(UTF_8));

            byte[] signature = privateSignature.sign();

            return Hex.toHexString(signature).toUpperCase();

    }

    @Override
    public boolean verifySignBytes15(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("NONEwithRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(UTF_8));

        byte[] signatureBytes = Hex.decode(signature);

        return publicSignature.verify(signatureBytes);

    }

//    public String signBytes15(String plainText, Key privKey) {
//        byte[] cipherText = null;
//
//        try {
//            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//
//            byte[] input = plainText.getBytes();
//            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
//            cipher.init(Cipher.ENCRYPT_MODE, privKey);
//            cipherText = cipher.doFinal(input);
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return Hex.toHexString(cipherText).toUpperCase();
//    }

//    public String verifySignBytes15(String cipherText, PublicKey pubKey) {
//        byte [] plainText = null;
//        try {
//
//            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//
////            byte[] input = cipherText.getBytes();
//            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
//
//            cipher.init(Cipher.DECRYPT_MODE, pubKey);
//            plainText = cipher.doFinal(cipherText.getBytes(UTF_8));
//
//
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return Hex.toHexString(plainText).toUpperCase();
//    }


}