package {{ cookiecutter.basePackage }}.common.util;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

/**
 * RSA工具类
 */
public final class RSAUtils {

    private static final String RSA_ALGORITHM = "RSA";
    private static final String RSA_PADDING = "RSA/ECB/PKCS1Padding";
    private static final String SIGN_ALGORITHM = "SHA256withRSA";
    private static final int KEY_SIZE = 2048;

    /**
     * 生成RSA密钥对
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 公钥加密
     */
    public static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     */
    public static byte[] decrypt(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encryptedData);
    }

    /**
     * 私钥签名
     */
    public static byte[] sign(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 公钥验签
     */
    public static boolean verify(byte[] data, byte[] signatureBytes, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(signatureBytes);
    }

    /**
     * 将公钥转换为Base64字符串
     */
    public static String publicKeyToBase64(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /**
     * 将私钥转换为Base64字符串
     */
    public static String privateKeyToBase64(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /**
     * 从Base64字符串还原公钥
     */
    public static PublicKey base64ToPublicKey(String base64PublicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 从Base64字符串还原私钥
     */
    public static PrivateKey base64ToPrivateKey(String base64PrivateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    // 以下为字符串处理的便捷方法 --------------------------------

    /**
     * 加密字符串（使用UTF-8编码）
     */
    public static String encrypt(String text, PublicKey publicKey) throws Exception {
        byte[] encryptedBytes = encrypt(text.getBytes(java.nio.charset.StandardCharsets.UTF_8), publicKey);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 解密字符串（使用UTF-8编码）
     */
    public static String decrypt(String encryptedText, PrivateKey privateKey) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = decrypt(encryptedBytes, privateKey);
        return new String(decryptedBytes, java.nio.charset.StandardCharsets.UTF_8);
    }

    /**
     * 生成签名（返回Base64字符串）
     */
    public static String signToBase64(String text, PrivateKey privateKey) throws Exception {
        byte[] signatureBytes = sign(text.getBytes(java.nio.charset.StandardCharsets.UTF_8), privateKey);
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    /**
     * 验证签名（输入Base64签名字符串）
     */
    public static boolean verifyFromBase64(String text, String base64Signature, PublicKey publicKey) throws Exception {
        byte[] signatureBytes = Base64.getDecoder().decode(base64Signature);
        return verify(text.getBytes(java.nio.charset.StandardCharsets.UTF_8), signatureBytes, publicKey);
    }

    // 示例用法
    public static void main(String[] args) throws Exception {
        // 生成密钥对
        KeyPair keyPair = generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 转换为Base64字符串
        String publicKeyStr = publicKeyToBase64(publicKey);
        String privateKeyStr = privateKeyToBase64(privateKey);
        System.out.println("Public Key: " + publicKeyStr);
        System.out.println("Private Key: " + privateKeyStr);

        // 加密解密示例
        String originalText = "Hello, RSA Encryption!";
        String encryptedText = encrypt(originalText, publicKey);
        String decryptedText = decrypt(encryptedText, privateKey);
        System.out.println("\nOriginal: " + originalText);
        System.out.println("Encrypted: " + encryptedText);
        System.out.println("Decrypted: " + decryptedText);

        // 签名验签示例
        String dataToSign = "Important data to sign";
        String signature = signToBase64(dataToSign, privateKey);
        boolean isValid = verifyFromBase64(dataToSign, signature, publicKey);
        System.out.println("\nSignature Valid: " + isValid);
    }
}