package br.gov.inmetro.beacon.nist.validator;

import br.gov.inmetro.beacon.nist.validator.marshalling.ObjectFactory;
import br.gov.inmetro.beacon.nist.validator.marshalling.RecordType;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.x509.X509StreamParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Scanner;

/**
 * Common logic for loading NIST random beacon records and certificates
 * 
 * @author David Soroko
 *
 */
public class Loader {


    /**
     * Loads an X509Certificate from a URL pointing at a PEM file. Both 'file' and 'https' protocols are supported.
     * 
     * @param url the url of the PEM file
     * @return the loaded certificate
     * @throws Exception
     */
    public static X509Certificate loadCert(final URL url) throws Exception {

        try (Scanner scanner = new Scanner(url.openStream()).useDelimiter("\\Z");
             PemReader pemReader = new PemReader(new StringReader(scanner.next()))) {

            X509StreamParser parser = X509StreamParser.getInstance("Certificate", "BC");
            parser.init(new ByteArrayInputStream(pemReader.readPemObject().getContent()));

            return (X509Certificate) parser.read();
        }
    }

    /**
     * Loads a RecordType from the specified URL
     * 
     * @param url the url of record file
     * @return the loaded record
     * @throws Exception
     */
    public static RecordType loadRecord(final URL url) throws Exception {

        JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller unMarshaller = context.createUnmarshaller();
        return (RecordType) ((JAXBElement) unMarshaller.unmarshal(url)).getValue();
    }
}
