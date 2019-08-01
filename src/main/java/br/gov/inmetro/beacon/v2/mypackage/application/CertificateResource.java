package br.gov.inmetro.beacon.v2.mypackage.application;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "beacon/2.0/certificate/", produces= MediaType.APPLICATION_JSON_VALUE)
public class CertificateResource {

    @RequestMapping
    public String getCertificate(){
        return "-----BEGIN CERTIFICATE-----\n" +
                "MIIGwTCCBamgAwIBAgIQBfnpjwe1SRtWx2HKo/WuVzANBgkqhkiG9w0BAQsFADBe\n" +
                "MQswCQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMRkwFwYDVQQLExB3\n" +
                "d3cuZGlnaWNlcnQuY29tMR0wGwYDVQQDExRHZW9UcnVzdCBSU0EgQ0EgMjAxODAe\n" +
                "Fw0xODA2MTkwMDAwMDBaFw0yMDA4MTMxMjAwMDBaMIGlMQswCQYDVQQGEwJCUjEX\n" +
                "MBUGA1UECBMOUmlvIGRlIEphbmVpcm8xGDAWBgNVBAcTD0R1cXVlIGRlIENheGlh\n" +
                "czFIMEYGA1UEChM/SW5zdGl0dXRvIE5hY2lvbmFsIGRlIE1ldHJvbG9naWEgUXVh\n" +
                "bGlkYWRlIGUgVGVjbm9sb2dpYSBJTk1FVFJPMRkwFwYDVQQDDBAqLmlubWV0cm8u\n" +
                "Z292LmJyMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArlnSJsrx7VgL\n" +
                "iWUJBhQL5n2heic5DdAxNxq0OP7Xm9MmgJ/dZ9YBvhPe+TEJgnL7CofYQkF6Jart\n" +
                "E+dzCTeSmJLoQqCE0WIptTG49Md8DlUpCkYN+Ap971DZr9KasZ6O8niLWrRTp08p\n" +
                "/itU2FeU8jK4KT4xbXPrEuwIGu5otir7b6TH14kjEJ4zctxMVJKttA318yaSa43K\n" +
                "Zqm+hVZZo/BjMjiqb9yo9py9Kif1NZGmhDxjqGcnDTTaesh0QuZb7QPSYh09TykT\n" +
                "wl8VEpYPsCvySaSy8nDaPaI/xUIqUiaJHIJ3nBhiRhHkHHZbISO9UN0qyIyypBnj\n" +
                "m57ZrOZjqwIDAQABo4IDMTCCAy0wHwYDVR0jBBgwFoAUkFj/sJx1qFFUd7Ht8qND\n" +
                "FjiebMUwHQYDVR0OBBYEFAzpSaQX8ijvIaZBH5+y2rgLL9SvMCsGA1UdEQQkMCKC\n" +
                "ECouaW5tZXRyby5nb3YuYnKCDmlubWV0cm8uZ292LmJyMA4GA1UdDwEB/wQEAwIF\n" +
                "oDAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwPgYDVR0fBDcwNTAzoDGg\n" +
                "L4YtaHR0cDovL2NkcC5nZW90cnVzdC5jb20vR2VvVHJ1c3RSU0FDQTIwMTguY3Js\n" +
                "MEwGA1UdIARFMEMwNwYJYIZIAYb9bAEBMCowKAYIKwYBBQUHAgEWHGh0dHBzOi8v\n" +
                "d3d3LmRpZ2ljZXJ0LmNvbS9DUFMwCAYGZ4EMAQICMHUGCCsGAQUFBwEBBGkwZzAm\n" +
                "BggrBgEFBQcwAYYaaHR0cDovL3N0YXR1cy5nZW90cnVzdC5jb20wPQYIKwYBBQUH\n" +
                "MAKGMWh0dHA6Ly9jYWNlcnRzLmdlb3RydXN0LmNvbS9HZW9UcnVzdFJTQUNBMjAx\n" +
                "OC5jcnQwCQYDVR0TBAIwADCCAX0GCisGAQQB1nkCBAIEggFtBIIBaQFnAHYAu9nf\n" +
                "vB+KcbWTlCOXqpJ7RzhXlQqrUugakJZkNo4e0YUAAAFkGRN9mAAABAMARzBFAiEA\n" +
                "hU5iuxbi9IrR7iLKEJnm2P1nfzsqo6iPYyXRmht/v1QCIGWYSUuntFZFRibHz+U4\n" +
                "kgiAyjRIouz7Q1ft9oiAHum9AHYAh3W/51l8+IxDmV+9827/Vo1HVjb/SrVgwbTq\n" +
                "/16ggw8AAAFkGRN+NwAABAMARzBFAiEAwnQbmC7Pnyqa1bNzglI9IjZUwmLTV40L\n" +
                "ZPCG6DG8+pgCIEIuz2abMfaQcrjMBgNNd1/FGrtfa7WHSw6XN1m+P85EAHUAb1N2\n" +
                "rDHwMRnYmQCkURX/dxUcEdkCwQApBo2yCJo32RMAAAFkGRN/MgAABAMARjBEAiAE\n" +
                "O+i55tfC87zq30zGEJR7w3+ZZqiRJdYBhDvsGGncXgIgUg+TXT2IwN5Htq5b2n/O\n" +
                "GNPWh4/bUwa+yIFGtFQ/YfowDQYJKoZIhvcNAQELBQADggEBACTEMRF6DMvUPLRt\n" +
                "Kg1TTIqEQnXzNk47CFAv4VqMkBK1NU/Zc1w/nHKZUZJ5b+wPn2tEEop3qHNNqT8F\n" +
                "IKppcNnOsJFYLZuVykkEuch2bHL1RXaTm2u+wSTdMbe4rKfp83+uwy6Y7+WXFIex\n" +
                "nU4dH8Psqn1W2v6grFQwf4JK7fFa5EPj37sTDdmZweY9al2I52f32vLAQRev9lrs\n" +
                "xSZEwYrjcQqRRkbpY6rr8G8054ntsX7CCOaVZmselvTMkwPNylEzcW6sZ/uuGGC0\n" +
                "VxraOoV/4mTvwUMK6rtazaoBIKTmbM1jcJM+rzbi0I6M6KYMaSwfdJJF79VEEL1P\n" +
                "SPUuHIs=\n" +
                "-----END CERTIFICATE-----";
    }

}
