package com.authentication.project1.accesskey;

import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

public class TokenSigner {
    public static String signToken(String payload, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(payload.getBytes());

        byte[] signedData = signature.sign();
        return Base64.getEncoder().encodeToString(signedData);
    }
}

