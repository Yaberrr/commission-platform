package com.moe.gateway.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtils {

    private static final Logger log = LoggerFactory.getLogger(RSAUtils.class);

    private static final Map<String, Key> RSA_KEY_MAP = new HashMap<>();

    public static final String KEY_ALGORITHM = "RSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";

    private static final String PRIVATE_KEY = "RSAPrivateKey";

    // 获取公钥
    public static PublicKey defaultPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Key key = RSA_KEY_MAP.get(PUBLIC_KEY);
        if (key != null) {
            return (PublicKey) key;
        }
        String keyStr = "-----BEGIN PUBLIC KEY-----" +
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwgNYiw3SFx3ahp9Q8M2AwvofK" +
                "8htEJuOOsUsryi+jqn1Oxg2paXG8NM9Y5gbbCQtKe10dfHfejTzdPtXlD02EmyVq" +
                "NCxMdjRM+O5bvlvRiVYhlWxr4CglPLf4vezc3iievcIIzh45TtREHOvdN2zdObuU" +
                "zFNI4RFLW0eP39BWyQIDAQAB" +
                "-----END PUBLIC KEY-----";

        String publicKeyPem = keyStr
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.decodeBase64(publicKeyPem);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return keyFactory.generatePublic(keySpec);

    }

    // 获取私钥
    public static PrivateKey defaultPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Key key = RSA_KEY_MAP.get(PRIVATE_KEY);
        if (key != null) {
            return (PrivateKey) key;
        }
        String keyStr = "-----BEGIN PRIVATE KEY-----" +
                "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALCA1iLDdIXHdqGn" +
                "1DwzYDC+h8ryG0Qm446xSyvKL6OqfU7GDalpcbw0z1jmBtsJC0p7XR18d96NPN0+" +
                "1eUPTYSbJWo0LEx2NEz47lu+W9GJViGVbGvgKCU8t/i97NzeKJ69wgjOHjlO1EQc" +
                "6903bN05u5TMU0jhEUtbR4/f0FbJAgMBAAECgYEAhyhRUdOMQfko3cdPnT3WNd1g" +
                "zMaynHCPJrCbGJA+BL1EMXM1+Pm2z2E9Oh0DOO1nCQUIm9C5VeGfrug0ELC0MUYe" +
                "h86vdGosAGyIAHPLk9TzjmIF0ONYQy9ScGyqCxSx5c4Q/rHdZ+ekJIpXEq8IWdOG" +
                "myV0dS9+NfAZsyzoNZECQQDhqciKAERIcetV++X+HoIbDmBjN5v26j2yPELZV8wt" +
                "wmCZbZeN/yFWq6CrjKSxZsVsLNpx2Da+1HAWJ1wmBJ0dAkEAyDs1ssQcfkszZF/h" +
                "ExYzrEB73m3zSA+O2tp4Z0VPdbIb9uz+Jj4w9vMUr3Ww7hI/6zk1kNM3C7WJaSGi" +
                "UfcsnQJAOBEyFS+Jx6IeQYfLlf7IfqZsxln7szZxy8XzKgMJZNklnRlAjXQ1OU59" +
                "JgJUrQsGxCDEyYJZ38Hyc16mI4AD9QJAbkguS0e4adcBd0dXsCuKSlU55EiqtgXb" +
                "qK6rojWKjkiLHnVphaa7UUiRHBK2YGmsyuG3nwy2RLy38i+UN5DsAQJAbLnJp17U" +
                "yPorT4eEbQzDOTrNcsvu01oJ+Gu01LPmMBIZk8L4MxL0vifgylv00T8MqDkOyzGX" +
                "/2g4+irJi/gCeg==" +
                "-----END PRIVATE KEY-----";

        String privateKeyPem = keyStr
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.decodeBase64(privateKeyPem);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 公钥加密
     * @param text   待加密的明文字符串
     * @param publicKey 公钥
     * @return 加密后的密文
     */
    public static String rsaEncrypt(String text, PublicKey publicKey) {
        try {
            log.info("明文字符串为:[{}]", text);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] tempBytes = cipher.doFinal(text.getBytes("UTF-8"));
            return Base64.encodeBase64String(tempBytes);
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + text + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     * @param secretText    待解密的密文字符串
     * @param privateKey 私钥
     * @return 解密后的明文
     */
    public static String rsaDecrypt(String secretText, PrivateKey privateKey) {
        try {
            // 生成私钥
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 密文解码
            byte[] secretTextDecoded = Base64.decodeBase64(secretText.getBytes("UTF-8"));
            byte[] tempBytes = cipher.doFinal(secretTextDecoded);
            return new String(tempBytes);
        } catch (Exception e) {
            log.error("解密字符串[" + secretText + "]时遇到异常: ", e);
            return null;
        }
    }

    /**
     * 私钥解密
     *
     * @param secretText    待解密的密文字符串
     * @return 解密后的明文
     */
    public static String rsaDecrypt(String secretText) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return rsaDecrypt(secretText, defaultPrivateKey());
    }
}


