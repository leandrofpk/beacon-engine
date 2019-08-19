package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import br.gov.inmetro.beacon.v1.domain.service.CriptoUtilService;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class SerializationTest {

    @Test
    public void testarSerializacaoHashCertifiedId(){

        String pemNist = "-----BEGIN CERTIFICATE-----\n" +
                "MIIHWzCCBkOgAwIBAgIQCrOnD+Dvk39rGwz3xX6StTANBgkqhkiG9w0BAQsFADBE\n" +
                "MQswCQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMR4wHAYDVQQDExVE\n" +
                "aWdpQ2VydCBHbG9iYWwgQ0EgRzIwHhcNMTgwMTEwMDAwMDAwWhcNMTkwMTExMTIw\n" +
                "MDAwWjCBozELMAkGA1UEBhMCVVMxETAPBgNVBAgTCE1hcnlsYW5kMRUwEwYDVQQH\n" +
                "EwxHYWl0aGVyc2J1cmcxNzA1BgNVBAoTLk5hdGlvbmFsIEluc3RpdHV0ZSBvZiBT\n" +
                "dGFuZGFyZHMgYW5kIFRlY2hub2xvZ3kxEDAOBgNVBAsTB0lUTC9DU0QxHzAdBgNV\n" +
                "BAMTFmVuZ2luZS5iZWFjb24ubmlzdC5nb3YwggIiMA0GCSqGSIb3DQEBAQUAA4IC\n" +
                "DwAwggIKAoICAQDqdbhEDfBOCzCjW6cKBSKkkl8pc7v6VDDI21VAs5fOyZRoSwfW\n" +
                "tHc9YkVoYBaNTfmUBW5Q8hWbk65VftCznjEFIa05ldME/ABGKSkQKFyC3ELsE4+e\n" +
                "nkM1I9EJOt9dCSH9dSmzwjFf8C/fxhGqYEatH8GenuQ/FbU7shiigiqHUJU9SSVZ\n" +
                "trH4qV7szmcIBd/VzVTgLFipF8nl6EoScEIdgOC+ZmRo0LLfB/ulUT7iaXuzB0GP\n" +
                "ocMjwk4yfJgHNkHitgGMoDNYGVz4sU0QCtQSAXjjvwAMb+EzGBV08Zj2qNMEANKX\n" +
                "cvdRRA340t3oC6PbmeW+7w+IRo0to8AqhUlSAobmty6pOUzykEdhg/g6FKOowQEz\n" +
                "JZkhHd1/7Fh7XRHvc6EKz5tjAP+c5MUP9ni6O2N6uzrNbm94p0JmGk6DwJVWS0A1\n" +
                "l7M/xjON3aZN2f2ZSlezurBh9GBWENniHUsG/iOJdtjb+VE8VCr+J3Ltn62CfbgU\n" +
                "4aW+XbwHtZq+jtLzf2VKgHpeM4LKCzgQbhSbJNuuNCm2ib8PaS/f+kz+p9D8Rd/q\n" +
                "Te4/w03a+bqZRBcql8K7n73ysT+r595oos6AQujj4rgOHU7byZDLRGbMHPhox4OD\n" +
                "Dd7iYGhMHcfUez7FYJF0zNQafEt7iZP6U/PigtPoi3lJNPLcijbNcFNSowIDAQAB\n" +
                "o4IC5zCCAuMwHwYDVR0jBBgwFoAUJG4rLdBqklFRJWkBqppHponnQCAwHQYDVR0O\n" +
                "BBYEFB69FZvO+nhCyDuDd1uLZchw4+j3MCEGA1UdEQQaMBiCFmVuZ2luZS5iZWFj\n" +
                "b24ubmlzdC5nb3YwDgYDVR0PAQH/BAQDAgWgMB0GA1UdJQQWMBQGCCsGAQUFBwMB\n" +
                "BggrBgEFBQcDAjB3BgNVHR8EcDBuMDWgM6Axhi9odHRwOi8vY3JsMy5kaWdpY2Vy\n" +
                "dC5jb20vRGlnaUNlcnRHbG9iYWxDQUcyLmNybDA1oDOgMYYvaHR0cDovL2NybDQu\n" +
                "ZGlnaWNlcnQuY29tL0RpZ2lDZXJ0R2xvYmFsQ0FHMi5jcmwwTAYDVR0gBEUwQzA3\n" +
                "BglghkgBhv1sAQEwKjAoBggrBgEFBQcCARYcaHR0cHM6Ly93d3cuZGlnaWNlcnQu\n" +
                "Y29tL0NQUzAIBgZngQwBAgIwdAYIKwYBBQUHAQEEaDBmMCQGCCsGAQUFBzABhhho\n" +
                "dHRwOi8vb2NzcC5kaWdpY2VydC5jb20wPgYIKwYBBQUHMAKGMmh0dHA6Ly9jYWNl\n" +
                "cnRzLmRpZ2ljZXJ0LmNvbS9EaWdpQ2VydEdsb2JhbENBRzIuY3J0MAkGA1UdEwQC\n" +
                "MAAwggEFBgorBgEEAdZ5AgQCBIH2BIHzAPEAdgC72d+8H4pxtZOUI5eqkntHOFeV\n" +
                "CqtS6BqQlmQ2jh7RhQAAAWDgpJnPAAAEAwBHMEUCIQC1eW0bNSeOZNYU8Of5/oZr\n" +
                "fruiiiIYK/QKct/hCsXgdwIgS5/nMOviHGu2/FLTMkWFAva+wBVGNrsRdbD6yLoQ\n" +
                "BNgAdwCHdb/nWXz4jEOZX73zbv9WjUdWNv9KtWDBtOr/XqCDDwAAAWDgpJm5AAAE\n" +
                "AwBIMEYCIQCq1AvkODGe/zTX56kishId42HRCiEDa1/Wq8F9/DOabwIhAI5UbEeE\n" +
                "Q20nuadFVxpJgirXFYpAzjlr6/emIXRc5E1LMA0GCSqGSIb3DQEBCwUAA4IBAQAs\n" +
                "WjP0Sj4r2nHWLKi45aUwhS+WZzq3cDPa92QDP6LnFTtzUicPpOyLYcWOsc7SKyGi\n" +
                "BlWgxHp9vmoO+25gCXSbet42Yl1PXFhTpZcPHPxO/BknRGe9CY1pmOOyjsxwMZ8a\n" +
                "qH2He7anCpHk5AEfLX0F+WHjEtnYBrRhMM6GtftHXXxuAhGuH0zmzbwakREOoWNO\n" +
                "Q2iTFVBb9UybxHbl9r0rVD3x5FpRrTf90l5dhrERzhtjone/DtQU/5wRagRTKjeQ\n" +
                "VaCu59An0vNCJYVWPbOypZCRdbcKlcd3GoMx2DosfFcdgSLR1h9+O0y3DsonJgiT\n" +
                "Q2l+vxzeaIsZqUEYrLaq\n" +
                "-----END CERTIFICATE-----";


        ByteBuffer allocate = ByteBuffer.allocate(64);


//        ByteUtils.

//        ByteBuffer allocate = ByteBuffer.allocate(64).put(pemNist.getBytes());
//        System.out.println("allocate");
//        System.out.println(allocate);

//        ByteUtils.

        byte[] bytes = ByteUtils.fromHexString(pemNist);
        System.out.println("bytes");
        System.out.println(bytes.length);
        System.out.println(bytes);

    }

    @Test
    public void zeroHTest(){
//        encode();
    }

    @Test
    public void encode() throws NoSuchAlgorithmException, IOException {
//    private void encode(int valor, int BLenHashBytes){
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

//        "5501e3d72bc42f3b96e16de4dcadcb16768e109662bd16d667d5fd9aee585af31bbdc5dd4f53592276064b53dddd76c8f3604b2a41db6e09f78f82bb5d6569e7".
        byte[] bytes = ("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000").getBytes();
//        byte[] bytes2 = ("5501e3d72bc42f3b96e16de4dcadcb16768e109662bd16d667d5fd9aee585af31bbdc5dd4f53592276064b53dddd76c8f3604b2a41db6e09f78f82bb5d6569e7").getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        System.out.println(length);



        String s = FileUtils.readFileToString(new File("nist-ans1.txt"));
        String hashNist = CriptoUtilService.hashSha512Hexa(s);
        System.out.println(hashNist);

    }



}
