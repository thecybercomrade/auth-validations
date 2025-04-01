package com.authentication.project1.accesskey;

import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class TokenVerifier {
    public static boolean verifyToken(String payload, String signedToken, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(payload.getBytes());

        byte[] signedBytes = Base64.getDecoder().decode(signedToken);
        return signature.verify(signedBytes);
    }
}

