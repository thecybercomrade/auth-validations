package com.authentication.project1.accesskey;

import com.azure.security.keyvault.keys.KeyClient;
import com.azure.security.keyvault.keys.KeyClientBuilder;
import com.azure.security.keyvault.keys.cryptography.CryptographyClient;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.keys.models.KeyVaultKey;


//az keyvault key create --vault-name MyKeyVault --name MyRSAKey --kty RSA --size 2048


//az keyvault key rotation-policy update --vault-name MyKeyVault --name MyRSAKey \
//        --expiry-time P30D --auto-rotate-policy AutomaticallyAfterExpiry



public class AzureKeyVaultExample {
    private static final String KEY_VAULT_URL = "https://<your-key-vault-name>.vault.azure.net/";

    public static void main(String[] args) {
        // Initialize Key Vault Client
        KeyClient keyClient = new KeyClientBuilder()
                .vaultUrl(KEY_VAULT_URL)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();

        // Fetch latest key version
        KeyVaultKey key = keyClient.getKey("MyRSAKey");

        System.out.println("Public Key: " + key.getKey().toString());
    }
}
