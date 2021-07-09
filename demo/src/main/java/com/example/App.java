package com.example;

import io.jsonwebtoken.*;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import java.util.*;
import java.security.*;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }


    public static Optional<RSAPublicKey> getParsedPublicKey(){
        // public key content...excluding '---PUBLIC KEY---' and '---END PUBLIC KEY---'
         String PUB_KEY =System.getenv("PUBLIC_KEY") ; 
 
        // removes white spaces or char 20
         String PUBLIC_KEY = "";
           if (!PUB_KEY.isEmpty()) {
             PUBLIC_KEY = PUB_KEY.replace(" ", "");
         }
 
         try {
             byte[] decode = com.google.api.client.util.Base64.decodeBase64(PUBLIC_KEY);
             X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(decode);
             KeyFactory keyFactory = KeyFactory.getInstance("RSA");
             RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(keySpecX509);
             return Optional.of(pubKey);
 
         } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
             e.printStackTrace();
             System.out.println("Exception block | Public key parsing error ");
             return Optional.empty();
         }
    }


    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Jws<Claims> jws;
        try {
            System.out.println("inside!");
            jws = Jwts.parser()
            .setSigningKey(
                "-----BEGIN CERTIFICATE-----" + 
            "MIIDqTCCApGgAwIBAgIEXbABozANBgkqhkiG9w0BAQsFADBkMQswCQYDVQQGEwJV"+
            "UzELMAkGA1UECAwCQ0ExFjAUBgNVBAcMDU1vdW50YWluIFZpZXcxDTALBgNVBAoM" +
            "BFdTTzIxDTALBgNVBAsMBFdTTzIxEjAQBgNVBAMMCWxvY2FsaG9zdDAeFw0xOTEw" +
            "MjMwNzMwNDNaFw0yMjAxMjUwNzMwNDNaMGQxCzAJBgNVBAYTAlVTMQswCQYDVQQI" +
            "DAJDQTEWMBQGA1UEBwwNTW91bnRhaW4gVmlldzENMAsGA1UECgwEV1NPMjENMAsG" +
            "A1UECwwEV1NPMjESMBAGA1UEAwwJbG9jYWxob3N0MIIBIjANBgkqhkiG9w0BAQEF" +
            "AAOCAQ8AMIIBCgKCAQEAxeqoZYbQ/Sr8DOFQ+/qbEbCp6Vzb5hzH7oa3hf2FZxRK" +
            "F0H6b8COMzz8+0mvEdYVvb/31jMEL2CIQhkQRol1IruD6nBOmkjuXJSBficklMaJ" +
            "ZORhuCrB4roHxzoG19aWmscA0gnfBKo2oGXSjJmnZxIh+2X6syHCfyMZZ00LzDyr" +
            "goXWQXyFvCA2ax54s7sKiHOM3P4A9W4QUwmoEi4HQmPgJjIM4eGVPh0GtIANN+BO" +
            "Q1KkUI7OzteHCTLu3VjxM0sw8QRayZdhniPF+U9n3fa1mO4KLBsW4mDLjg8R/JuA" +
            "GTX/SEEGj0B5HWQAP6myxKFz2xwDaCGvT+rdvkktOwIDAQABo2MwYTAUBgNVHREE" +
            "DTALgglsb2NhbGhvc3QwHQYDVR0OBBYEFEDpLB4PDgzsdxD2FV3rVnOr/A0DMB0G" +
            "A1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjALBgNVHQ8EBAMCBPAwDQYJKoZI" +
            "hvcNAQELBQADggEBAE8H/axAgXjt93HGCYGumULW2lKkgqEvXryP2QkRpbyQSsTY" +
            "cL7ZLSVB7MVVHtIsHh8f1C4Xq6Qu8NUrqu5ZLC1pUByaqR2ZIzcj/OWLGYRjSTHS" +
            "VmVIq9QqBq1j7r6f3BWqaOIiknmTzEuqIVlOTY0gO+SHdS62vr2FCz4yOrBEulGA" +
            "vomsU8sqg4PhFnkhxI4M912Ly+2RgN9L7AkhzK+EzXY1/QtlI/VysNfS6zrHasKz" +
            "6CrKKCGqQnBnSvSTyF9OR5KFHnkAwE995IZrcSQicMxsLhTMUHDLQ/gRyy7V/ZpD" +
            "MfAWR+5OeQiNAp/bG4fjJoTdoqkul51+2bHHVrU=" +
            "-----END CERTIFICATE-----").parseClaimsJws("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ik5UZG1aak00WkRrM05qWTBZemM1TW1abU9EZ3dNVEUzTVdZd05ERTVNV1JsWkRnNE56YzRaQT09Iiwia2lkIjoiTlRkbVpqTTRaRGszTmpZMFl6YzVNbVptT0Rnd01URTNNV1l3TkRFNU1XUmxaRGc0TnpjNFpBPT1fUlMyNTYifQ==.eyJhdXQiOiJBUFBMSUNBVElPTiIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2FwaW5hbWUiOiJIb3NwaXRhbFNlcnZpY2VBUEkiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9hcHBsaWNhdGlvbnRpZXIiOiIxMFBlck1pbiIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL3ZlcnNpb24iOiJ2MSIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2tleXR5cGUiOiJTQU5EQk9YIiwiaXNzIjoid3NvMi5vcmdcL3Byb2R1Y3RzXC9hbSIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2FwcGxpY2F0aW9ubmFtZSI6IlRlc3RBUFAiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9lbmR1c2VyIjoiYWRtaW5AY2FyYm9uLnN1cGVyIiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvZW5kdXNlclRlbmFudElkIjoiLTEyMzQiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9hcHBsaWNhdGlvblVVSWQiOiIwZTU5NzEzOC05ZTIyLTQzNDAtYTU3MS0yZTY0Yjk0NTE4YmQiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9zdWJzY3JpYmVyIjoiYWRtaW4iLCJhenAiOiIzSVRsb3VEMFVTX1ZmZGNDQXl0YTFhUkZRRlFhIiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvdGllciI6IkdvbGQiLCJzY29wZSI6ImFtX2FwcGxpY2F0aW9uX3Njb3BlIGRlZmF1bHQiLCJleHAiOjE2MjU4MDg2NDYsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2FwcGxpY2F0aW9uaWQiOiIyIiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvdXNlcnR5cGUiOiJBcHBsaWNhdGlvbl9Vc2VyIiwiaWF0IjoxNjI1ODA3NzQ2LCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9hcGljb250ZXh0IjoiXC9ob3NwaXRhbFwvdjEifQ==.NSCkdnuxDYcW66Lq_VKUwgmIRwVEe05_haXQuYofbNG7skwypZxk4gU_BrvvtbuXoRsPBmq_9H1YbRXNzuQ3nKUA8wIw1j731piQKTLoVr6-lzBmO703hUl0vLttZ2EzWS0r6r3cm5TcDqZXCQoQcJfePPs-f2eU9CgbRXoNLVb5JiHRqkJI5euRxRucxR0rpgmoHFBmTA9s_CT6Tnno-b61xjiCmOnXgCQQLCTZn0WOAj6VJkbovmM1JcADyDK2IBJNi38NrRppPZZFBC-GPRjoQkL3v_0ZYV9m5mtSQTx3grPr52JwSqmKFJVVtM2OytRzl8D4qbRE1F6lNbStIA==");
             
            System.out.println(jws);

            if (jws != null) {
              Set<String> claimsKeys =  jws.getBody().keySet();

              System.out.println(claimsKeys);  

            }

            // we can safely trust the JWT

        } catch (JwtException ex) {       // (5)
            System.out.println(ex);
        }
    }
}
