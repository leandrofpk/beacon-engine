package br.gov.inmetro.beacon.v2.mypackage.application;

import br.gov.inmetro.beacon.v2.mypackage.PulseType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

@RestController
@RequestMapping(value = "beacon/2.0/chain/{idChain}/pulse")
public class ChainsResource {

    @RequestMapping("/last")
    public PulseType last(@PathVariable Integer idChain) throws DatatypeConfigurationException {
        final PulseType pulseType = new PulseType();
        pulseType.setUri("https://beacon.nist.gov/beacon/2.0/chain/1/pulse/460459");
        pulseType.setVersion("Version 2.0");
        pulseType.setCipherSuite(0);
        pulseType.setPeriod(60000);
        pulseType.setCertificateId("5501e3d72bc42f3b96e16de4dcadcb16768e109662bd16d667d5fd9aee585af31bbdc5dd4f53592276064b53dddd76c8f3604b2a41db6e09f78f82bb5d6569e7");
        pulseType.setChainIndex(1);
        pulseType.setPulseIndex(460460);

        final GregorianCalendar from = GregorianCalendar.from(ZonedDateTime.now());
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(from);
        pulseType.setTimeStamp(date2);

        pulseType.setLocalRandomValue("4DB42C57AC2E282313AA0582C4329CC793E6D7E5226CDC17CC4DDC31AF194C029954F91CAC9C67A97DD0D9E935B64A0CB793A5A73C013BC8A03BE096A070A61D");

        final PulseType.External external = new PulseType.External();

        external.setSourceId("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        external.setStatusCode(new Short("0"));
        external.setValue("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");

        pulseType.setExternal(external);

        return pulseType;
    }

}
