package br.gov.inmetro.beacon.v2.mypackage.infra.util.suite0;

import br.gov.inmetro.beacon.v1.domain.RSA;
import br.gov.inmetro.beacon.v1.domain.service.CriptoUtilService;
import br.gov.inmetro.beacon.v2.mypackage.infra.util.CipherSuiteBuilder;
import br.gov.inmetro.beacon.v2.mypackage.infra.util.ICipherSuite;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.interfaces.RSAPublicKey;

import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.springframework.util.Base64Utils;

import static br.gov.inmetro.beacon.v1.domain.service.CriptoUtilService.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertTrue;

public class CipherSuiteZeroTest {

    String plainText = "88569C283D277F82C06D8CC262CB19FC6F5C73C31922F5AB28275891D4FA6C5B2EC7EA0F9AC15CD61A38D889C71042F05591CF32D6B233D4D9D9F36531F10356";
    String cipherText = "9F98A5F334EFD4238410449C2880E79844A318FD3B83E9A8D0C0834F9C97D7D2" +
            "9DD1A04A2C320B6CED9A37DECC87CE6CB0124FCB9651B95C4C04DC146959A199" +
            "5D88A1AA7A3C853E68871A9F5F7B448D6FF2EE1E013F07BF0A4FCB5DC4AA84DC" +
            "4D7B9FB6BD95DB09A29D95F7D6B0C110C9E555BBA60BDF8217BDD719BD5441CF" +
            "52D71BD1DF1B3A12385A05902BDA18DB33F62FA039CC926745A50CD970708C64" +
            "01EE01B5506C2FF37E8851BDC4FE2B8A7DFBD77B985F2C5EE1B7358C60EDDAF3" +
            "500F15CAFC0764509259CD8FA9E55CAA4944FE04E63E6216F9166B4C3506FEEF" +
            "00C0C76023A2375C6980D9A077F9942035BDBE1DFDB0CEBB42F676C1FA96C740" +
            "907F0100181357835DE6B5C7AA12903FE7548AE223DB0F479BBF91B1F6E79552" +
            "65F5FB8D842F0E43AA8D7D2211798754762A7C6FBF05DA9B44EF69A75C6FD4AA" +
            "7767D62306B3965A5CA77ABE86F0D24E7FDA11D43DA7D0BC5378598F03C22095" +
            "3FEF0002727CFD8CF1FE18F7E61CB6F40548409210317B08EC6E616457CAFE88" +
            "913C601373F56AAD8010ED93043EDA1A08828102A5EB11C90CF7FDD43F30BA54" +
            "12048E7CB2DAEDB412B58619F738880844398F80C85B2BEF497530F8CCCFF05F" +
            "21711CB2077CFB669077F3855C84C2C4330B4EC2987EB5E6C1FE5B344900570A" +
            "A30550C3B8B0592F1796602F40E713059BC732792C91678F2B6E5BD75FD38FEE";

    @Test
    public void signBytes15Test() throws Exception {

//        PrivateKey privKey = CriptoUtilService.loadPrivateKey("D:\\inmetro\\beacon-keys\\nova-chave\\priv-key-pkcs8.pem");
        PrivateKey privKey = CriptoUtilService.loadPrivateKey("D:\\inmetro\\beacon-keys\\4096-module\\beacon-priv-pkcs8.pem");
        ICipherSuite cipherSuite = CipherSuiteBuilder.build(0);

        String result = cipherSuite.signBytes15(plainText, privKey);
        Assert.assertEquals(cipherText, result);
    }


    @Test
    public void verifySignBytes15Test() throws Exception {
        RSAPublicKey publicKey = RSA.getPublicKey("D:\\inmetro\\beacon-keys\\nova-chave\\pub-key.pem");
        ICipherSuite cipherSuite = CipherSuiteBuilder.build(0);

        String result = cipherSuite.verifySignBytes15(cipherText, publicKey);

        Assert.assertEquals(plainText, result);
    }


    @Test
    public void outroteste() throws Exception {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
        keyGen.initialize(4096, new SecureRandom());
        KeyPair keyPair = keyGen.generateKeyPair();

        Signature signature = Signature.getInstance("SHA512withRSA", "BC");
        signature.initSign(keyPair.getPrivate());

        byte[] message = plainText.getBytes();
        signature.update(message);

        byte[] sigBytes = signature.sign();
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPublic());

        byte[] decSig = cipher.doFinal(sigBytes);
        ASN1InputStream aIn = new ASN1InputStream(decSig);
        ASN1Sequence seq = (ASN1Sequence) aIn.readObject();

        System.out.println("Assinatura:" + Hex.toHexString(decSig));

        System.out.println(ASN1Dump.dumpAsString(seq));

        MessageDigest hash = MessageDigest.getInstance("SHA-512", "BC");
        hash.update(message);

        ASN1OctetString sigHash = (ASN1OctetString) seq.getObjectAt(1);
        System.out.println(MessageDigest.isEqual(hash.digest(), sigHash.getOctets()));

    }

    @Test // FUNCIONANDO
    public void testePKCS1BouceCastle() throws Exception {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        String privateKeyFile = "D:\\inmetro\\beacon-keys\\4096-module\\beacon-priv.pem";

        PEMParser pemParser = new PEMParser(new FileReader(privateKeyFile));
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
        Object object = pemParser.readObject();
        KeyPair kp = converter.getKeyPair((PEMKeyPair) object);
        PrivateKey privateKey = kp.getPrivate();

        System.out.println(privateKey);

        String hashSha512Hexa = "5C571D1B7641A359DE56A2498D4B972F4AFD6C85752381790E575E70B3BA7CBD7F5D6C646675F48C696884B0381FDC751C6153102EA14023F2719E23FE0C931C";

        String signature = signReturnHex(hashSha512Hexa, privateKey);
        System.out.println(signature);


        PublicKey publicKey = loadPublicKeyFromCertificate("D:\\inmetro\\beacon-keys\\4096-module\\beacon.cer");
//        PublicKey publicKey = loadPublicKeyFromCertificate("D:\\inmetro\\beacon-keys\\4096-module\\nist.cer");







        //Let's check the signature
        boolean isCorrect = CriptoUtilService.verifyReturnHex(hashSha512Hexa, signature, publicKey);

        assertTrue(isCorrect);



    }

    @Test
    public void testeHasCertificateId() throws NoSuchAlgorithmException, IOException {

//        D:\inmetro\beacon-keys\4096-module

//        PublicKey publicKey = loadPublicKeyFromCertificate("D:\\inmetro\\beacon-keys\\4096-module\\nist-v2.cer");

//        System.out.println(publicKey.getAlgorithm());



//        Base64.decode(publicKey.getEncoded());
//        String s = Base64Utils.encodeToString(publicKey.getEncoded());
//        String s1 = hashSha512Hexa(s);
//        System.out.println(s1);

        ICipherSuite build = CipherSuiteBuilder.build(0);

        System.out.println("Bytes");
//        byte[] bytes = Files.readAllBytes(Paths.get("D:\\inmetro\\beacon-keys\\4096-module\\nist-v2-pubkey.pem"));
        byte[] bytes = Files.readAllBytes(Paths.get("D:\\inmetro\\beacon-keys\\4096-module\\nist-v2-pubkey-semCabecalho.pem"));
        String digest = build.getDigest(bytes);
        System.out.println("Digest bytes:" + digest);


//        String s1 = Base64Utils.encodeToString(bytes);
//        System.out.println("Base 64 from ChaveSemCabecalho");
//        System.out.println(s1);

        System.out.println("String");
//        String s = FileUtils.readFileToString(new File("D:\\inmetro\\beacon-keys\\4096-module\\nist-v2-pubkey.pem"));
        String s = FileUtils.readFileToString(new File("D:\\inmetro\\beacon-keys\\4096-module\\nist-v2-pubkey-semCabecalho.pem"));
        System.out.println(s);

        System.out.println("Digest string:" + build.getDigest(s));



        System.out.println("DER");
//        String s = FileUtils.readFileToString(new File("D:\\inmetro\\beacon-keys\\4096-module\\nist-v2-pubkey.pem"));
        byte[] bytesDer = Files.readAllBytes(Paths.get("D:\\inmetro\\beacon-keys\\4096-module\\nist-v2-public_key.der"));




        String digest1 = build.getDigest(bytesDer);

        System.out.println("Digest DER:" + digest1);

    }


}