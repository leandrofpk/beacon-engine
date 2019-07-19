package br.gov.inmetro.beacon.v1.domain.service;

import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.FileUtils.readFileToString;

public class CriptoUtilService {

    public static String hashSha512Hexa(String originalString) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] encodedhash = digest.digest(originalString.toUpperCase().getBytes(UTF_8));

        String hex = bytesToHex(encodedhash);

        return hex.toUpperCase();
    }


    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("NONEwithRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(UTF_8));

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("NONEwithRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return publicSignature.verify(signatureBytes);
    }

    public static String signBytes(byte[] plainTextBytes, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA512withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainTextBytes);

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    public static String signBytes15(byte[] plainTextBytes, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA512withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainTextBytes);

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    public static boolean verifyBytes(byte[] plainTextInBytes, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA512withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainTextInBytes);

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return publicSignature.verify(signatureBytes);
    }

    public static PrivateKey loadPrivateKey(String file) throws Exception {
        String privateKeyPEM = readFileToString(new File(file), StandardCharsets.UTF_8);

        // strip of header, footer, newlines, whitespaces
        privateKeyPEM = privateKeyPEM
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        // decode to get the binary DER representation
        byte[] privateKeyDER = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyDER));
        return privateKey;
    }

    public static PublicKey loadPublicKeyFromCertificate(String certificatePath) throws IOException, CertificateException {
        InputStream inStream = new FileInputStream(certificatePath);
        X509Certificate cert = X509Certificate.getInstance(inStream);
        final PublicKey publicKey = cert.getPublicKey();
        inStream.close();
        return publicKey;
    }

    public static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
