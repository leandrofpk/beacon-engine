package br.gov.inmetro.beacon.engine.infra.util.suite0;

import br.gov.inmetro.beacon.engine.infra.util.ICipherSuite;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.bouncycastle.util.encoders.Hex;
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
            return Hex.toHexString(encodedhash);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getDigest(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] encodedhash = digest.digest(input);
            return Hex.toHexString(encodedhash);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String sign(PrivateKey privateKey, byte[] message) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        Signature signature = Signature.getInstance("SHA256withRSA", "BC");
        signature.initSign(privateKey);
        signature.update(message);

        byte[] sigBytes = signature.sign();

        return Hex.toHexString(sigBytes);
    }

    @Override
    public boolean verify(PublicKey publicKey, String sign, byte[] message) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        byte[] decSig = cipher.doFinal(ByteUtils.fromHexString(sign));
        ASN1InputStream aIn = new ASN1InputStream(decSig);
        ASN1Sequence seq = (ASN1Sequence) aIn.readObject();

        MessageDigest hash = MessageDigest.getInstance("SHA-256", "BC");
        hash.update(message);

        ASN1OctetString sigHash = (ASN1OctetString) seq.getObjectAt(1);
        return MessageDigest.isEqual(hash.digest(), sigHash.getOctets());
    }

}