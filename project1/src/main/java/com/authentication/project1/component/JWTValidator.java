package com.authentication.project1.component;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JWTValidator {

    private static final String JWKS_URL = "https://thecybercomrade.us.auth0.com/.well-known/jwks.json"; // Replace if using Microsoft
    private static final Map<String, RSAKey> keyCache = new ConcurrentHashMap<>();

    public boolean validateToken(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);
        String keyId = signedJWT.getHeader().getKeyID();

        RSAKey rsaKey = keyCache.computeIfAbsent(keyId, this::fetchPublicKey);
        if (rsaKey == null) {
            return false;
        }

        JWSVerifier verifier = new RSASSAVerifier(rsaKey.toRSAPublicKey());
        return signedJWT.verify(verifier);
    }

    private RSAKey fetchPublicKey(String keyId) {
        try {
            JWKSet jwkSet = JWKSet.load(new URL(JWKS_URL));
            JWK jwk = jwkSet.getKeyByKeyId(keyId);
            if (jwk instanceof RSAKey) {
                return (RSAKey) jwk;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


