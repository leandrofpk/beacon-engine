package br.gov.inmetro.beacon.nist.validator;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.X509Certificate;

/**
 * Provides the validation logic for a beacon record signature, output value and previous value contract
 * 
 * @author David Soroko
 *
 */
public class Validator {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private X509Certificate certificate;
    private PublicKey publicKey;

    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
        this.publicKey = certificate.getPublicKey();

        System.out.println(publicKey);

    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    /**
     * 
     * @param record the record to validate
     * @return true if the signature of the payload is valid
     * @throws Exception
     */
    public boolean isSignatureValid(UnpackedRecord record) throws Exception {

        final ByteArrayOutputStream baos = new ByteArrayOutputStream(2048); // should be enough
        baos.write(record.getVersion().getBytes(StandardCharsets.US_ASCII));
        baos.write(record.getFrequencyAsBytes());
        baos.write(record.getTimeStampAsBytes());
        baos.write(record.getSeedAsBytes());
        baos.write(record.getPreviousOutputAsBytes());
        baos.write(record.getStatusCodeAsBytes());

        Signature signatureObject = Signature.getInstance("SHA512withRSA");
        signatureObject.initVerify(publicKey);
        signatureObject.update(baos.toByteArray());

        // reverse is necessary b/c Beacon signs with Microsoft CryptoAPI which outputs the signature as little-endian
        // instead of big-endian
        return signatureObject.verify(reverse(record.getSignatureAsBytes()));
    }

    /**
     * 
     * @param record the record to validate
     * @return true if the output value is valid given the signature
     * @throws Exception
     */
    public boolean isOutputValueValid(UnpackedRecord record) throws Exception {
        MessageDigest mda = MessageDigest.getInstance("SHA-512", "BC");
        return Arrays.areEqual(mda.digest(record.getSignatureAsBytes()), record.getOutputAsBytes());
    }

    /**
     * 
     * @param record a record in a chain
     * @param nextRecord the next record in a chain 
     * @return true if the value chaining is correct
     * @throws Exception
     */
    public boolean isChainingValid(UnpackedRecord record, UnpackedRecord nextRecord) throws Exception {
        return nextRecord.getPreviousOutputValue().equals(record.getOutputValue());
    }

    private byte[] reverse(final byte[] bytes) {
        int i = 0;
        int j = bytes.length - 1;
        byte tmp;
        while (j > i) {
            tmp = bytes[j];
            bytes[j] = bytes[i];
            bytes[i] = tmp;
            j--;
            i++;
        }
        return bytes;
    }
}
