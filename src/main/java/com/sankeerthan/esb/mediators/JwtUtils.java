package com.sankeerthan.esb.mediators;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Base64;
import java.util.Optional;

public class JwtUtils {

  private static final String DOT= "\\.";
  private static final char JWT_PARTS_COUNT = 3;
  private static final char JWT_HEADER_INDEX = 0;
  private static final char JWT_BODY_INDEX = 1;
  private static final char JWT_SIG_INDEX = 2;

  private static final String ENCRYT_ALGO = "SHA256withRSA";

  public static boolean isValidJwt(String jwtToken) {
    if (jwtToken == null || jwtToken.isEmpty()) {
      return false;
    }
    String [] parts = jwtToken.split(DOT);
    if (parts == null || parts.length != JWT_PARTS_COUNT) {
      return false;
    }
    return true;
  }

  public static String getJwtHeader(String jwtToken) {
    if (isValidJwt(jwtToken)) {
      String [] parts = jwtToken.split(DOT);
      return parts[JWT_HEADER_INDEX];
    }
    return "";


  }

  //Todo: rf
  public static String getJwtBody(String jwtToken) {
    if (jwtToken == null || jwtToken.isEmpty()) {
      return "";
    }
    String [] parts = jwtToken.split(DOT);
    if (parts == null || parts.length != JWT_PARTS_COUNT) {
      return "";
    }
    return parts[JWT_BODY_INDEX];
  }

  //todo: rf
  public static String getJwtSignature(String jwtToken) {
    if (jwtToken == null || jwtToken.isEmpty()) {
      return "";
    }
    String [] parts = jwtToken.split(DOT);
    if (parts == null || parts.length != JWT_PARTS_COUNT) {
      return "";
    }
    return parts[JWT_SIG_INDEX];
  }



  //todo: test
  public static boolean verifySignature(String jwtHeader, String jwtBody, String jwtSig, PublicKey publicKey)
      throws NoSuchAlgorithmException, InvalidKeyException, SignatureException
  {
    final Signature signature = Signature.getInstance(ENCRYT_ALGO);
    signature.initVerify(publicKey);


    String msg = jwtHeader + "." + jwtBody;

    signature.update(msg.getBytes(StandardCharsets.UTF_8));

    return signature.verify(Base64.getUrlDecoder().decode(jwtSig));

  }




  public static Optional<PublicKey> getParsedPublicKey(String key){

    // removes white spaces or char 20
    if (!key.isEmpty()) {
      key = key.replace(" ", "");
    }
    key = key.replaceAll("\\n", "");

    try {

      String certb64 = "...";

      byte[] certder = Base64.getMimeDecoder().decode(key);
      InputStream certstream = new ByteArrayInputStream(certder);
      Certificate cert = CertificateFactory.getInstance("X.509").generateCertificate(certstream);
      PublicKey pub = cert.getPublicKey();

      return Optional.of(pub);

    } catch (CertificateException e) {
      e.printStackTrace();
      System.out.println("Exception block | Public key parsing error ");
      return Optional.empty();
    }
  }



}

