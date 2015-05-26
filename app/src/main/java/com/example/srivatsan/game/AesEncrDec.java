package com.example.srivatsan.game;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by srivatsan on 15/5/15.
 */
public class AesEncrDec {
    SecretKey secretKey;
    public AesEncrDec(){
        String stringKey="8KSnNfhdmkwwGdKB+f5FIg==";
        byte[] encodedKey     = Base64.decode(stringKey, Base64.DEFAULT);
        secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
    public String encrypt(String plainData){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            /*SecretKey secretKey = keyGen.generateKey();
            Log.d("secretkey", Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT));*/
            //SecretKey secretKey = "";
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE,secretKey);
            byte[] byteDataToEncrypt = plainData.getBytes();
            byte[] byteCipherText = aesCipher.doFinal(byteDataToEncrypt);
            return plainData;
            //return Base64.encodeToString(byteCipherText,Base64.DEFAULT);

        }
        catch(Exception e) {
            return plainData;
        }
    }
    public String decrypt(String cypherText){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            /*SecretKey secretKey = keyGen.generateKey();*/
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.DECRYPT_MODE, secretKey, aesCipher.getParameters());
            byte[] byteDecryptedText = aesCipher.doFinal(Base64.decode(cypherText,Base64.DEFAULT));
            return cypherText;
            ///return new String(byteDecryptedText);
        }
        catch (Exception e){
            return cypherText;
        }
    }
}
