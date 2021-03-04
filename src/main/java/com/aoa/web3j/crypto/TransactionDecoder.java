package com.aoa.web3j.crypto;

import com.alibaba.fastjson.JSON;
import com.aoa.web3j.core.protocol.core.methods.request.AssetInfo;
import com.aoa.web3j.rlp.RlpDecoder;
import com.aoa.web3j.rlp.RlpList;
import com.aoa.web3j.rlp.RlpString;
import com.aoa.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.Charset;

public class TransactionDecoder {

    public static RawTransaction decode(String hexTransaction) {
        byte[] transaction = Numeric.hexStringToByteArray(hexTransaction);
        RlpList rlpList = RlpDecoder.decode(transaction);
        RlpList values = (RlpList) rlpList.getValues().get(0);
        BigInteger nonce = ((RlpString) values.getValues().get(0)).asBigInteger();
        BigInteger gasPrice = rlpStringToBigInteger((RlpString) values.getValues().get(1));
        BigInteger gasLimit = rlpStringToBigInteger((RlpString) values.getValues().get(2));
        String to = ((RlpString) values.getValues().get(3)).asString();
        BigInteger value = rlpStringToBigInteger((RlpString) values.getValues().get(4));
        String data = ((RlpString) values.getValues().get(5)).asString();
        BigInteger action = ((RlpString) values.getValues().get(6)).asBigInteger();
        byte[] vote = ((RlpString) values.getValues().get(7)).getBytes();
        byte[] nickname = ((RlpString) values.getValues().get(8)).getBytes();
        String asset = ((RlpString) values.getValues().get(9)).asString();
        byte[] assetInfo = ((RlpString) values.getValues().get(10)).getBytes();
        String subAddress = ((RlpString) values.getValues().get(11)).asString();
        String abi = ((RlpString) values.getValues().get(12)).asString();
        if (values.getValues().size() > 13) {
            byte v = ((RlpString) values.getValues().get(13)).getBytes()[0];
            byte[] r = zeroPadded(((RlpString) values.getValues().get(14)).getBytes(), 32);
            byte[] s = zeroPadded(((RlpString) values.getValues().get(15)).getBytes(), 32);
            Sign.SignatureData signatureData = new Sign.SignatureData(v, r, s);
            //return new SignedRawTransaction(nonce, gasPrice, gasLimit, to, value, data, signatureData,Action
            // .getAction(action.intValue()));

            String s1 = new String(assetInfo, Charset.forName("UTF-8"));
            AssetInfo assetObject = JSON.parseObject(s1, AssetInfo.class);
            byte[] a = new byte[1];
            a[0] = v;
            System.err.printf("v:%s\n",Numeric.toHexString(a));
            System.err.printf("r:%s\n",Numeric.toHexString(r));
            System.err.printf("s:%s\n",Numeric.toHexString(s));

            return new SignedRawTransaction(nonce, gasPrice, gasLimit, to, value, data,
                                            Action.getAction(action.intValue()), vote, nickname, asset, assetObject,
                                            abi, signatureData);
        } else {
            return RawTransaction.createTransaction(nonce, gasPrice, gasLimit, to, value, data);
        }
    }

    public static BigInteger rlpStringToBigInteger(RlpString rlpString) {
        String s2 = bytesToHexFun2(rlpString.getBytes());
        return Numeric.decodeQuantity("0x"+s2);
    }

    public static String bytesToHexFun2(byte[] bytes) {
        char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for(byte b : bytes) { // 利用位运算进行转换，可以看作方法一的变种
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
        }

        return new String(buf);
    }


    private static byte[] zeroPadded(byte[] value, int size) {
        if (value.length == size) {
            return value;
        }
        int diff = size - value.length;
        byte[] paddedValue = new byte[size];
        System.arraycopy(value, 0, paddedValue, diff, value.length);
        return paddedValue;
    }
}
