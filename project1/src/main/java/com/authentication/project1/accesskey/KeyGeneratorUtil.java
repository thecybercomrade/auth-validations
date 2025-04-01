package com.authentication.project1.accesskey;

import java.security.*;
import java.util.Base64;

public class KeyGeneratorUtil {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

        System.out.println("Private Key: " + privateKey);
        System.out.println("Public Key: " + publicKey);
    }
}
