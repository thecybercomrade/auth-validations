package com.authentication.project1.component;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JWTValidator {

    private static final String JWKS_URL = "https://thecybercomrade.us.auth0.com/.well-known/jwks.json";
    private static final String HS256_SECRET = "gKffsAN9pUzaFHtO1lzzQAfQ3fxa5P4qWSipY2Ck6kI=";  // If using HS256
    private static final Map<String, RSAKey> keyCache = new ConcurrentHashMap<>();

    public boolean validateToken(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSAlgorithm algorithm = signedJWT.getHeader().getAlgorithm();

        // If using RS256 (Auth0 Default)
        if (JWSAlgorithm.RS256.equals(algorithm)) {
            String keyId = signedJWT.getHeader().getKeyID();
            if (keyId == null) {
                throw new IllegalArgumentException("Missing 'kid' in JWT header for RS256 verification.");
            }

            RSAKey rsaKey = keyCache.computeIfAbsent(keyId, this::fetchPublicKey);
            if (rsaKey == null) {
                return false;
            }

            JWSVerifier verifier = new RSASSAVerifier(rsaKey.toRSAPublicKey());
            return signedJWT.verify(verifier);
        }

        // If using HS256 (Shared Secret)
        if (JWSAlgorithm.HS256.equals(algorithm)) {
            JWSVerifier verifier = new MACVerifier(HS256_SECRET);
            return signedJWT.verify(verifier);
        }

        throw new UnsupportedOperationException("Unsupported JWT signing algorithm: " + algorithm);
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
