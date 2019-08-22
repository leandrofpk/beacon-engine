package br.gov.inmetro.beacon.v1.domain.service;

import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.encoders.Hex;

import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import java.io.*;
import java.security.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class CriptoUtilService {

    public static String hashSha512Hexa(String originalString) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] encodedhash = digest.digest(originalString.toUpperCase().getBytes(UTF_8));

        String hex = bytesToHex(encodedhash);

        return hex.toUpperCase();
    }


//    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
//        Signature privateSignature = Signature.getInstance("NONEwithRSA");
//        privateSignature.initSign(privateKey);
//        privateSignature.update(plainText.getBytes(UTF_8));
//
//        byte[] signature = privateSignature.sign();
//
//        return Base64.getEncoder().encodeToString(signature);
//    }

    public static String signReturnHex(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("NONEwithRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(UTF_8));

        byte[] signature = privateSignature.sign();

        return Hex.toHexString(signature).toUpperCase();
    }

    public static boolean verifyReturnHex(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("NONEwithRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(UTF_8));

        byte[] signatureBytes = Hex.decode(signature);

        return publicSignature.verify(signatureBytes);
    }

//    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
//        Signature publicSignature = Signature.getInstance("NONEwithRSA");
//        publicSignature.initVerify(publicKey);
//        publicSignature.update(plainText.getBytes(UTF_8));
//
//        byte[] signatureBytes = Base64.getDecoder().decode(signature);
//
//        return publicSignature.verify(signatureBytes);
//    }
//
//    public static String signBytes(byte[] plainTextBytes, PrivateKey privateKey) throws Exception {
//        Signature privateSignature = Signature.getInstance("SHA512withRSA");
//        privateSignature.initSign(privateKey);
//        privateSignature.update(plainTextBytes);
//
//        byte[] signature = privateSignature.sign();
//
//        return Base64.getEncoder().encodeToString(signature);
//    }

//    public static String signBytes15(byte[] plainTextBytes, PrivateKey privateKey) throws Exception {
//        Signature privateSignature = Signature.getInstance("SHA512withRSA");
//        privateSignature.initSign(privateKey);
//        privateSignature.update(plainTextBytes);
//
//        byte[] signature = privateSignature.sign();
//
//        return Base64.getEncoder().encodeToString(signature);
//    }
//
//    public static boolean verifyBytes(byte[] plainTextInBytes, String signature, PublicKey publicKey) throws Exception {
//        Signature publicSignature = Signature.getInstance("SHA512withRSA");
//        publicSignature.initVerify(publicKey);
//        publicSignature.update(plainTextInBytes);
//
//        byte[] signatureBytes = Base64.getDecoder().decode(signature);
//
//        return publicSignature.verify(signatureBytes);
//    }

    public static PrivateKey loadPrivateKeyPkcs1(String privateKeyFile) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        PEMParser pemParser = new PEMParser(new FileReader(privateKeyFile));
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
        Object object = pemParser.readObject();
        KeyPair kp = converter.getKeyPair((PEMKeyPair) object);
        PrivateKey privateKey = kp.getPrivate();

        return privateKey;
    }


//    public static PrivateKey loadPrivateKey(String file) throws Exception {
//        String privateKeyPEM = readFileToString(new File(file), StandardCharsets.UTF_8);
//
//        // strip of header, footer, newlines, whitespaces
//        privateKeyPEM = privateKeyPEM
//                .replace("-----BEGIN PRIVATE KEY-----", "")
//                .replace("-----END PRIVATE KEY-----", "")
//                .replaceAll("\\s", "");
//
//        // decode to get the binary DER representation
//        byte[] privateKeyDER = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(privateKeyPEM);
//
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyDER));
//        return privateKey;
//    }

    public static PublicKey loadPublicKeyFromCertificate(String certificatePath) {
        PublicKey publicKey = null;
        try (InputStream inStream = new FileInputStream(certificatePath)) {
            X509Certificate cert = X509Certificate.getInstance(inStream);

//            System.out.println(cert);

            publicKey = cert.getPublicKey();
//            inStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | CertificateException e) {
            e.printStackTrace();
        }
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
