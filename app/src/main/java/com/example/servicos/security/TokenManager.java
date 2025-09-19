package com.example.servicos.security;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class TokenManager {

    private static final String KEY_ALIAS = "MyAppKeyAlias";
    private static final String ANDROID_KEYSTORE = "AndroidKeyStore";
    // ALGORITMO/MODO/PADDING
    // Algoritmo: AES, RSA, DES
    // Modo de operação, define como o algoritmo de bloco é aplicado
    // ao bloco de dados: ECB, CBC, GCM
    // Padding: Esquema de preenchimento utilizado para garantir que
    // os dados a serem criptografados tenham um tamanho adequado
    // para o algoritmo
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static KeyStore keyStore;

    /**
     * Gera uma chave AES no Keystore (se ainda não existir).
     */
    public static void generateKey() {
        try {
            if (keyStore == null) {
                // Retorna um objeto do tipo KeyStore do tipo específicado
                keyStore = getKeyStore();
            }

            // Carrega a KeyStore
            keyStore.load(null);

            // Verifica se já não existe a KeyStore usando o alias
            // senão, cria uma nova
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(
                        KeyProperties.KEY_ALGORITHM_AES,
                        ANDROID_KEYSTORE
                );

                // Utilizado para inicialização do KeyPairGenerator ou do KeyGenerator
                // do sistema KeyStore do Android. A especificação determina quais são
                // as operações autorizadas (ex: criptografar; descriptografar )
                // e, se é necessário autenticação para utilização das chaves
                KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(
                        KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT
                )
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setUserAuthenticationRequired(false)
                        .build();

                // Inicializa o gerador com o conjuto de parâmetros especificados
                keyGenerator.init(keyGenParameterSpec);
                // Gera a chave secreta
                keyGenerator.generateKey();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Criptografa um token e retorna IV + dados.
     */
    public static EncryptedData encryptToken(String token) {
        try {
            if (keyStore == null) {
                keyStore = getKeyStore();
            }
            keyStore.load(null);
            // Recupera uma chave associada a um alias. A chave deve ter sido
            // associada com o alias com o método: setKeyEntry ou setEntry
            // passando uma PrivateKeyEntry ou SecretKeyEntry
            SecretKey secretKey = (SecretKey) keyStore.getKey(KEY_ALIAS, null);
            // Recupera um objeto Chipher especificando uma transformação
            // O Chiper é o responsável por fornecer a codificação necessária
            // a criptografia ou descriptografia ocorrer
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            // Inicializa o Chiper
            // Podendo ser inicializado como: criptografar, descriptografar,
            // empacotador de chave e desempacotador de chave.
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] iv = cipher.getIV();
            // Executa o processamento de criptografia ou descriptografia
            byte[] encryptedData = cipher.doFinal(token.getBytes(StandardCharsets.UTF_8));

            return new EncryptedData(iv, encryptedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Descriptografa um token usando IV + dados criptografados.
     */
    public static String decryptToken(byte[] iv, byte[] encryptedData) {
        try {
            if (keyStore == null) {
                keyStore = getKeyStore();
            }
            keyStore.load(null);

            SecretKey secretKey = (SecretKey) keyStore.getKey(KEY_ALIAS, null);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec spec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);

            byte[] decryptedData = cipher.doFinal(encryptedData);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Classe auxiliar para retornar IV + dados criptografados.
     */
    public static class EncryptedData {
        public final byte[] iv;
        public final byte[] data;

        public EncryptedData(byte[] iv, byte[] data) {
            this.iv = iv;
            this.data = data;
        }
    }

    private static KeyStore getKeyStore() throws KeyStoreException {
        return KeyStore.getInstance(ANDROID_KEYSTORE);
    }
}