package com.example.demo.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 3des即为3重des，所以秘钥为3*8 24字节,如果对接文档超过24，可能java部分转换秘钥会自动截
 */
public class ThreeDESUtil {

    /**
     * 转换成十六进制字符串
     *
     * @param key
     * @return
     */
    public static byte[] hex(String key) {
        String f = DigestUtils.md5Hex(key);
        byte[] bkeys = new String(f).getBytes();
        byte[] enk = new byte[24];
        for (int i = 0; i < 24; i++) {
            enk[i] = bkeys[i];
        }
        return enk;
    }

    /**
     * 3DES加密
     *
     * @param key    密钥，24位
     * @param srcStr 将加密的字符串
     * @return
     */
    public static String encode3Des(String key, String srcStr) {
        byte[] keybyte = key.getBytes();
        byte[] src = srcStr.getBytes();
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
            //加密
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(Cipher.ENCRYPT_MODE, deskey);

            return Base64.encodeBase64String(c1.doFinal(src));
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 3DES解密
     *
     * @param key    加密密钥，长度为24字节
     * @param desStr 解密后的字符串
     * @return lee on 2017-08-09 10:52:54
     */
    public static String decode3Des(String key, String desStr) {
        Base64 base64 = new Base64();
        byte[] keybyte = key.getBytes();
        byte[] src = base64.decode(desStr);

        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
            //解密
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return new String(c1.doFinal(src));
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(encode3Des("123456788765432112345678", "123456789"));
        System.out.println(decode3Des("123456788765432112345678", "XMc0LwyPL4W1+mtXH5s5rw=="));
    }
}
