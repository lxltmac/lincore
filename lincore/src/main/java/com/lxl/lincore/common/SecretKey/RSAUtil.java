package com.lxl.lincore.common.SecretKey;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import static com.lxl.lincore.common.SecretKey.RSACoder.getKeyString;
import static com.lxl.lincore.common.SecretKey.RSACoder.getPrivateKey;
import static com.lxl.lincore.common.SecretKey.RSACoder.getPublicKey;

/**
 * @author linxili
 * @Description
 * @Date: 2019/3/20 17:27
 */
public class RSAUtil {

    public static void main(String[] args) throws Exception {
        Map<String,String> map = getKeyMap();
        String publicKeyString = map.get("publicKeyString");
        System.out.println("public:\n" + publicKeyString);
        String privateKeyString = map.get("privateKeyString");
        System.out.println("private:\n" + privateKeyString);
        byte[] enBytes = encrypt(publicKeyString,"9168628072160912");
        String s1 = new String(enBytes);
        System.out.println("加密后的密文：" + s1);
        deEncrypt(privateKeyString,enBytes);
    }


    /**
     * @Author linxili
     * @Description: RSA加密
     * @Date: 2019/3/20
     * @param text
     * @return: void
     **/
    public static byte[] encrypt(String publicKeyString ,String text) throws Exception {
        //加解密类
        Cipher cipher = Cipher.getInstance("RSA");
        //明文
        byte[] plainText = text.getBytes();
        //加密
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKeyString));
        byte[] enBytes = cipher.doFinal(plainText);
//        String s1 = new String(enBytes);
//        System.out.println("加密后的密文：" + s1);
        return enBytes;
    }

    /**
     * @Author linxili
     * @Description: RSA解密
     * @Date: 2019/3/20
    * @param privateKeyString
    * @param text
     * @return: void
     **/
    public static void deEncrypt(String privateKeyString,byte[] enByte) throws Exception {
        //加解密类
        Cipher cipher = Cipher.getInstance("RSA");

        byte[] enBytes = enByte;
        //通过密钥字符串得到密钥
        PrivateKey privateKey = getPrivateKey(privateKeyString);
        //解密
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //明文
        byte[]deBytes = cipher.doFinal(enBytes);
        String s = new String(deBytes);
        System.out.println("解密后的明文：" + s);
    }

    /**
     * @Author linxili
     * @Description: 获取钥对
     * @Date: 2019/3/21
     * @param
     * @return: java.util.Map<java.lang.String,java.lang.String>
     **/
    public static Map<String,String> getKeyMap() throws Exception{
        Map<String,String> map = new HashMap<>();
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        //密钥位数
        keyPairGen.initialize(1024);
        //密钥对
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKeyString = getKeyString(publicKey);
        System.out.println("public1:\n" + publicKeyString);
        String privateKeyString = getKeyString(privateKey);
        System.out.println("private1:\n" + privateKeyString);
        map.put("publicKeyString",publicKeyString);
        map.put("privateKeyString",privateKeyString);
        return map;
    }
}
