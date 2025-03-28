package com.moe.auth;

import com.moe.auth.service.AppLoginService;
import com.moe.common.core.utils.RSAUtils;
import com.moe.user.api.IUserApi;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.regex.Pattern;

import static com.moe.common.core.utils.RSAUtils.KEY_ALGORITHM;

/**
 * @author tangyabo
 * @date 2025/3/12
 */
@SpringBootTest(classes = MoeAuthApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestAuth {

    @Autowired
    private AppLoginService appLoginService;
    @Autowired
    private IUserApi userApi;
    @Autowired
    private RSAUtils rsaUtils;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d*$");


    @Test
    public void testSendCode(){
       /* User user = new User();
        user.setPhoneNumber("13316022418");
        userApi.saveUser(user);*/
//        appLoginService.sendCode("19073170919");


    }

    /**
     * 生成密文
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String publicKeyPem = ("-----BEGIN PUBLIC KEY-----" +
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwgNYiw3SFx3ahp9Q8M2AwvofK" +
                "8htEJuOOsUsryi+jqn1Oxg2paXG8NM9Y5gbbCQtKe10dfHfejTzdPtXlD02EmyVq" +
                "NCxMdjRM+O5bvlvRiVYhlWxr4CglPLf4vezc3iievcIIzh45TtREHOvdN2zdObuU" +
                "zFNI4RFLW0eP39BWyQIDAQAB" + "-----END PUBLIC KEY-----")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");
        byte[] encoded = Base64.decodeBase64(publicKeyPem);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        String text = "{randomStr:" + Math.random() + ",time:"+ System.currentTimeMillis()+"}";
        byte[] tempBytes = cipher.doFinal(text.getBytes("UTF-8"));
        System.out.println(Base64.encodeBase64String(tempBytes));
    }
}
