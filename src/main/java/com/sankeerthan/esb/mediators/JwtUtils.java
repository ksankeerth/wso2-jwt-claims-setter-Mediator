package com.sankeerthan.esb.mediators;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

public class JwtUtils {

  private static final String DOT= "\\.";
  private static final char JWT_PARTS_COUNT = 3;
  private static final char JWT_HEADER_INDEX = 0;
  private static final char JWT_BODY_INDEX = 1;
  private static final char JWT_SIG_INDEX = 2;

  private static final String ENCRYT_ALGO = "RSA";

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

    final byte [] jwtSigBytes =Base64.getDecoder().decode(jwtSig.getBytes(StandardCharsets.UTF_8));

    return signature.verify(jwtSigBytes);

  }



}

