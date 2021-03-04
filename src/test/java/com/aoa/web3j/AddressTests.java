package com.aoa.web3j;



import com.aoa.web3j.crypto.Credentials;
import com.aoa.web3j.crypto.ECKeyPair;
import com.aoa.web3j.crypto.Keys;

import org.junit.Test;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yujian    2020/05/20
 */
public class AddressTests {

    @Test
    public void createAddress() throws Exception {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        Credentials credentials = Credentials.create(ecKeyPair);
        System.err.printf("address:%s\n", credentials.getAddress());
        // 16进制
        System.err.printf("publicKey:%s\n", credentials.getEcKeyPair().getPublicKey().toString(16));
        System.err.printf("privateKey:%s\n", credentials.getEcKeyPair().getPrivateKey().toString(16));

        System.err.printf("publicKey:%s\n", credentials.getEcKeyPair().getPublicKey());
        System.err.printf("privateKey:%s\n", credentials.getEcKeyPair().getPrivateKey());

        //支持子地址,正常地址 + 32位MD5值
    }



    @Test
    public void getAddressByPrivateKey() throws Exception {
        // 16进制私钥
        String privateKey = "92e8798a13f06adc23fa690410e999b2a9aecb0d4883136b63da643e8a8c5ef2";
        BigInteger b = new BigInteger(privateKey, 16);
        // AOAf12f2e4457f1cdd0ad7c7874e0ff25d5d495b65a
        ECKeyPair ecKeyPair = ECKeyPair.create(b);
        Credentials credentials = Credentials.create(ecKeyPair);
        System.err.printf("address:%s\n", credentials.getAddress());
    }

    @Test
    public void verifyAddress() throws Exception {
        String address = "AOAf12f2e4457f1cdd0ad7c7874e0ff25d5d495b65a";

        Pattern pattern = Pattern.compile("(?i:^aoa|0x)[0-9a-f]{40}[0-9A-Za-z]{0,32}$");
        Matcher matcher = pattern.matcher(address.toLowerCase());
        System.out.println(matcher.find());


    }
}
