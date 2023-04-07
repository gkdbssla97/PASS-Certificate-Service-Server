package com.example.miniProj2.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCipher {
    // 알고리즘/모드/패딩
    private static final String algorithm = "AES/CBC/PKCS5Padding";
    // 암호화 키
    private SecretKey secretKey;
    // 초기화 벡터
    private IvParameterSpec iv;
    // 문자인코딩 방식
    private final String charset = "UTF-8";
    public AESCipher(String aesKey) {
        if(aesKey == null){
            throw new NoSecretKeyException("No SecretKey, Please Set SecretKey !!!");
        }
        if(aesKey.length() > 16) {
            this.iv = new IvParameterSpec(aesKey.substring(0, 16).getBytes());
        } else {
            this.iv = new IvParameterSpec(aesKey.getBytes());
        }
        this.secretKey = new SecretKeySpec(aesKey.getBytes(), "AES");
    }
    // 암호화
    public String encrypt(String str) throws Exception {
        Cipher c = Cipher.getInstance(algorithm);
        c.init(Cipher.ENCRYPT_MODE, this.secretKey, this.iv);
        return new String(Base64.encodeBase64(c.doFinal(str.getBytes(charset))));
    }
    // 복호화
    public String decrypt(String str) throws Exception {
        Cipher c = Cipher.getInstance(algorithm);
        c.init(Cipher.DECRYPT_MODE, this.secretKey, this.iv);
        return new String(c.doFinal(Base64.decodeBase64(str.getBytes())), charset);
    }
}
class NoSecretKeyException extends RuntimeException {
    public NoSecretKeyException(String msg) {
        super(msg);
    }
}
