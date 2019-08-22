package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import br.gov.inmetro.beacon.v1.domain.service.CriptoUtilService;
import br.gov.inmetro.beacon.v2.mypackage.infra.util.CipherSuiteBuilder;
import br.gov.inmetro.beacon.v2.mypackage.infra.util.ICipherSuite;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PulseAssinatiraAndPkcs15Test {

    @Test
    public void testeAsn1(){




    }

    @Test
    public void loadCertificate() throws IOException {
//        InputStream inStream = new FileInputStream("beaconv2.cer");

        String s = FileUtils.readFileToString(new File("beaconv2.cer"));
        ASN1InputStream input = new ASN1InputStream(s.getBytes(UTF_8));

        ASN1Primitive p;
        while ((p = input.readObject()) != null) {
            System.out.println(ASN1Dump.dumpAsString(p));
        }


//        System.out.println(ASN1Dump.dumpAsString(seq));

//        ICipherSuite build = CipherSuiteBuilder.build(0);
//        String digest = build.getDigest(inStream.toString());
//        System.out.println(digest);

//        try (InputStream inStream = new FileInputStream("beaconv2.cer")) {
//            X509Certificate cert = X509Certificate.getInstance(inStream);
//
//            System.out.println(cert.toString());
//
//
////            publicKey = cert.getPublicKey();
////            inStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException | CertificateException e) {
//            e.printStackTrace();
//        }
////        return publicKey;
    }


    @Test
    public void testarCertificadoNist() throws IOException {
        PublicKey publicKey = CriptoUtilService.loadPublicKeyFromCertificate("beaconv2.cer");

        System.out.println("pk:" + publicKey);
        System.out.println("pk s:" + publicKey.toString());

        ICipherSuite build = CipherSuiteBuilder.build(0);
        String digest = build.getDigest(publicKey.toString());
        System.out.println("digest:" + digest);

        System.out.println(publicKey.getFormat());
        System.out.println(Hex.toHexString(publicKey.getEncoded()));

        ASN1InputStream aIn = new ASN1InputStream(publicKey.getEncoded());

        ASN1Sequence seq = (ASN1Sequence) aIn.readObject();

        System.out.println(ASN1Dump.dumpAsString(seq));

    }


    //    https://8gwifi.org/RSAFunctionality?keysize=4096
    @Test
    public void testarAssinaturaNist() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
        keyGen.initialize(4096, new SecureRandom());
        KeyPair keyPair = keyGen.generateKeyPair();

        Signature signature = Signature.getInstance("SHA512withRSA", "BC");
        signature.initSign(keyPair.getPrivate());

//        byte[] message = "abc".getBytes();
        byte[] message =  "88569C283D277F82C06D8CC262CB19FC6F5C73C31922F5AB28275891D4FA6C5B2EC7EA0F9AC15CD61A38D889C71042F05591CF32D6B233D4D9D9F36531F10356".getBytes(UTF_8);
        signature.update(message);
        byte[] sigBytes = signature.sign();
        System.out.println(Hex.toHexString(sigBytes));


    }


    //    http://www.java2s.com/Tutorial/Java/0490__Security/BasicclassforexploringPKCS1V15Signatures.htm
    @Test
    public void validarAssinaturaNist() throws Exception{
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
        keyGen.initialize(512, new SecureRandom());
        KeyPair keyPair = keyGen.generateKeyPair();

        Signature signature = Signature.getInstance("SHA256withRSA", "BC");
//        Signature signature = Signature.getInstance("SHA256withRSA", "BC");
        signature.initSign(keyPair.getPrivate());

        byte[] message = "abc".getBytes();
        signature.update(message);

        byte[] sigBytes = signature.sign();

        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPublic());


        byte[] decSig = cipher.doFinal(sigBytes);
        ASN1InputStream aIn = new ASN1InputStream(decSig);
        ASN1Sequence seq = (ASN1Sequence) aIn.readObject();

        System.out.println("ASN1Dump:");
        System.out.println(ASN1Dump.dumpAsString(seq));

        MessageDigest hash = MessageDigest.getInstance("SHA-256", "BC");
        hash.update(message);

        ASN1OctetString sigHash = (ASN1OctetString) seq.getObjectAt(1);
        System.out.println(MessageDigest.isEqual(hash.digest(), sigHash.getOctets()));

        System.out.println(hash);
        System.out.println(hash.getAlgorithm());
        System.out.println(hash.getDigestLength());
        System.out.println(hash.digest());

        String s = Hex.toHexString(decSig);
        System.out.println("Assintatura");
        System.out.println(s);


    }

    //    http://www.java2s.com/Tutorial/Java/0490__Security/RSAexamplewithPKCS1Padding.htm
    @Test
    public void outroTeste() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

//        byte[] input = "abc".getBytes();
        byte[] input =  "88569C283D277F82C06D8CC262CB19FC6F5C73C31922F5AB28275891D4FA6C5B2EC7EA0F9AC15CD61A38D889C71042F05591CF32D6B233D4D9D9F36531F10356".getBytes(UTF_8);
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
//        SecureRandom random = new SecureRandom();
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");

//        generator.initialize(1024, random);
        generator.initialize(4096);

        KeyPair pair = generator.generateKeyPair();
        Key pubKey = pair.getPublic();
        Key privKey = pair.getPrivate();

        System.out.println("priv:" + pair.getPrivate().toString());
        System.out.println("pub:" + pair.getPublic().toString());

        cipher.init(Cipher.ENCRYPT_MODE, privKey);
        byte[] cipherText = cipher.doFinal(input);
//        System.out.println("cipher: " + new String(cipherText));
        System.out.println("cipher: " + Hex.toHexString(cipherText).toUpperCase());

        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        byte[] plainText = cipher.doFinal(cipherText);
        System.out.println("plain : " + new String(plainText));

    }

//    @Test
//    public void outroTeste2() throws Exception {
//        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//
//        byte[] input =  "88569C283D277F82C06D8CC262CB19FC6F5C73C31922F5AB28275891D4FA6C5B2EC7EA0F9AC15CD61A38D889C71042F05591CF32D6B233D4D9D9F36531F10356".getBytes(UTF_8);
//        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
//
//        Key pubKey = CriptoUtilService.loadPublicKeyFromCertificate("/home/leandro/dev/beacon-keys/4096-module/beacon.cer");
//        Key privKey = CriptoUtilService.loadPrivateKey("/home/leandro/dev/beacon-keys/4096-module/beacon-priv-pkcs8.pem");
//
//        System.out.println("pub:" + pubKey.toString());
//        System.out.println("priv:" + privKey.toString());
//
//        cipher.init(Cipher.ENCRYPT_MODE, privKey);
//        byte[] cipherText = cipher.doFinal(input);
//        System.out.println("cipher: " + Hex.toHexString(cipherText).toUpperCase());
//
//        cipher.init(Cipher.DECRYPT_MODE, pubKey);
//        byte[] plainText = cipher.doFinal(cipherText);
//        System.out.println("plain : " + new String(plainText));
//    }


    //    https://stackoverflow.com/questions/43459993/how-do-i-generate-rsa-key-pair-in-java-in-openssl-format
    @Test
    public void teste() throws Exception{

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(4096); KeyPair kp = kpg.generateKeyPair();

        System.out.println ("-----BEGIN PRIVATE KEY-----");
        System.out.println (Base64.getMimeEncoder().encodeToString( kp.getPrivate().getEncoded()));
        System.out.println ("-----END PRIVATE KEY-----");
        System.out.println ("-----BEGIN PUBLIC KEY-----");
        System.out.println (Base64.getMimeEncoder().encodeToString( kp.getPublic().getEncoded()));
        System.out.println ("-----END PUBLIC KEY-----");
    }


}
