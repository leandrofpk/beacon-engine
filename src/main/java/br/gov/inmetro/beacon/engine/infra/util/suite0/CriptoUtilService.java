package br.gov.inmetro.beacon.engine.infra.util.suite0;

import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import java.io.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

public class CriptoUtilService {

    public static PrivateKey loadPrivateKeyPkcs1(String privateKeyFile) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        PEMParser pemParser = new PEMParser(new FileReader(privateKeyFile));
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
        Object object = pemParser.readObject();
        KeyPair kp = converter.getKeyPair((PEMKeyPair) object);
        PrivateKey privateKey = kp.getPrivate();

        return privateKey;
    }

    public static PublicKey loadPublicKeyFromCertificate(String certificatePath) {
        PublicKey publicKey = null;
        try (InputStream inStream = new FileInputStream(certificatePath)) {
            X509Certificate cert = X509Certificate.getInstance(inStream);
            publicKey = cert.getPublicKey();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | CertificateException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

}
