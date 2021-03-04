package com.aoa.web3j.crypto;

import com.aoa.web3j.core.protocol.core.methods.request.AssetInfo;
import com.aoa.web3j.core.protocol.core.methods.request.Transaction;

import java.math.BigInteger;
import java.security.SignatureException;

public class SignedRawTransaction extends RawTransaction {

    private static final int CHAIN_ID_INC = 35;
    private static final int LOWER_REAL_V = 27;

    private Sign.SignatureData signatureData;

    public SignedRawTransaction(BigInteger nonce, BigInteger gasPrice,
                                BigInteger gasLimit, String to, BigInteger value, String data,
                                Sign.SignatureData signatureData,Action action) {
        super(nonce, gasPrice, gasLimit, to, value, data,null,action);
        this.signatureData = signatureData;
    }

    public SignedRawTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
                                BigInteger value, String data, Action action, byte[] vote, byte[] nickname, String asset,
                                AssetInfo assetInfo, String abi, Sign.SignatureData signatureData){
        super(nonce,gasPrice,gasLimit,to,value,data,action,vote,nickname,asset,assetInfo,abi);
        this.signatureData = signatureData;
    }

    public Sign.SignatureData getSignatureData() {
        return signatureData;
    }

    public String getFrom() throws SignatureException {
        Integer chainId = getChainId();
        byte[] encodedTransaction;
        if (null == chainId) {
            encodedTransaction = TransactionEncoder.encode(this);
        } else {
            encodedTransaction = TransactionEncoder.encode(this, chainId.byteValue());
        }
        byte v = signatureData.getV();
        byte[] r = signatureData.getR();
        byte[] s = signatureData.getS();
        Sign.SignatureData signatureDataV = new Sign.SignatureData(getRealV(v), r, s);
        BigInteger key = Sign.signedMessageToKey(encodedTransaction, signatureDataV);
        return "0x" + Keys.getAddress(key);
    }

    public void verify(String from) throws SignatureException {
        String actualFrom = getFrom();
        if (!actualFrom.equals(from)) {
            throw new SignatureException("from mismatch");
        }
    }

    private byte getRealV(byte v) {
        if (v == LOWER_REAL_V || v == (LOWER_REAL_V + 1)) {
            return v;
        }
        byte realV = LOWER_REAL_V;
        int inc = 0;
        if ((int) v % 2 == 0) {
            inc = 1;
        }
        return (byte) (realV + inc);
    }

    public Integer getChainId() {
        byte v = signatureData.getV();
        if (v == LOWER_REAL_V || v == (LOWER_REAL_V + 1)) {
            return null;
        }
        Integer chainId = (v - CHAIN_ID_INC) / 2;
        return chainId;
    }
}
