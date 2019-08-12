package br.gov.inmetro.beacon.v2.mypackage.infra.util;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 *  Local implementation for message digest
 */
public class Sha512InternalUtil implements ISha512 {

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

    public String signBytes15(String plainText, PrivateKey privateKey) {
        byte[] signature = null;
        try {
            Signature privateSignature = null;
//            privateSignature = Signature.getInstance("RSA/None/PKCS1Padding");
            privateSignature = Signature.getInstance("RSA/ECB/PKCS1Padding");

            privateSignature.initSign(privateKey);
            privateSignature.update(plainText.getBytes(UTF_8));

            privateSignature.sign();

        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(signature);
    }

}