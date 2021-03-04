package com.aoa.web3j;

import com.aoa.web3j.core.protocol.Web3j;
import com.aoa.web3j.core.protocol.core.DefaultBlockParameterName;
import com.aoa.web3j.core.protocol.core.methods.request.AssetInfo;
import com.aoa.web3j.core.protocol.core.methods.response.AOASendTransaction;
import com.aoa.web3j.core.protocol.http.HttpService;
import com.aoa.web3j.core.tx.ChainId;
import com.aoa.web3j.core.utils.AOAGas;
import com.aoa.web3j.crypto.Credentials;
import com.aoa.web3j.crypto.RawTransaction;
import com.aoa.web3j.crypto.TransactionDecoder;
import com.aoa.web3j.crypto.TransactionEncoder;
import com.aoa.web3j.utils.Convert;
import com.aoa.web3j.utils.Numeric;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

/**
 * @author yujian    2020/05/20
 */
public class RpcMethodRawTransactionTests {

    private Web3j web3j;

    @Before
    public void init() {
        web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));
    }

    private final String testAddress = "AOA2e25d6f13330163134b9e321491ca0d45233e054";
    private final String testPublicKey =
        "d12e322b4c9aa37c09883c957561f1a68a474957823f5a27f21fbd0ba22e830c411f1e1ccd0058cb9b52e20c0123555fb8fdd9817b48143c070d080328d46fa5";
    private final String testPrivateKey = "b968d08d67b3664bebd6b11b517557a29186e3c5f8474959d5db7cc0442b1831";


    @Test
    public void sendEMRawTransaction() throws Exception {
        String from = testAddress;
        // String to = "AOA8ea2354ba012628dd1dad9e44500a70075664a16";
        // 带子地址的to
        String to = "AOA8ea2354ba012628dd1dad9e44500a70075664a16" + "202cb962ac59075b964b07152d234b70";
        Credentials credentials = Credentials.create(testPrivateKey);
//        BigInteger nonce = web3j.emGetTransactionCount(from, DefaultBlockParameterName.LATEST)
//                                .sendAsync().get().getTransactionCount();
        BigInteger nonce = new BigInteger("0");


        BigInteger value = Convert.toWei("1.01", Convert.Unit.AOA).toBigInteger();
        BigInteger gas = AOAGas.defaultTrxGas.toBigInteger();
        BigInteger gasPrice = AOAGas.defaultGasPrice.toBigInteger();
        RawTransaction rawTransaction = RawTransaction.createAOATransaction(nonce, gasPrice, gas, to, value);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ChainId.MAINNET, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        System.err.printf("sign Hex:%s\n", hexValue);

        RawTransaction transaction = TransactionDecoder.decode(hexValue);
    }

    @Test
    public void offlineSignTransaction() throws Exception {
        String from = testAddress;
        // String to = "AOA8ea2354ba012628dd1dad9e44500a70075664a16";
        // 带子地址的to
        String to = "AOA8ea2354ba012628dd1dad9e44500a70075664a16" + "202cb962ac59075b964b07152d234b70";
        Credentials credentials = Credentials.create(testPrivateKey);
        BigInteger nonce = new BigInteger("2");

        BigInteger value = Convert.toWei("1.01", Convert.Unit.AOA).toBigInteger();
        BigInteger gas = AOAGas.defaultTrxGas.toBigInteger();
        BigInteger gasPrice = AOAGas.defaultGasPrice.toBigInteger();
        RawTransaction rawTransaction = RawTransaction.createAOATransaction(nonce, gasPrice, gas, to, value);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ChainId.MAINNET, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        System.err.printf("sign Hex: %s\n", hexValue);
    }

    @Test
    public void testDecodeRawSign() throws Exception {
        // String hexRaw = "0xf8bd0284ee6b28008261a8948ea2354ba012628dd1dad9e44500a70075664a16880e043da617250000808080808080b84a454d3865613233353462613031323632386464316461643965343435303061373030373536363461313632303263623936326163353930373562393634623037313532643233346237308026a0b96da6a1b931cd63c1d53a26610d4b24df8d3fb6c71722c84413c911fcdadd4ba05de7e209bde928614bf49d031d03600d973eae5daf8447a6f32607cf2809744c";
        String hexRaw = "0xf8b7018609184e72a0008261a8942b31c44ccbb50b27b2b283970393f4a3da153a1401808080808080b84a454d3262333163343463636262353062323762326232383339373033393366346133646131353361313431373137376261373530663064376561356537303435653337616266633664628026a02245739637683beabca28341ab586e7a92f6d82040efd90a57b09df38e3c428aa003db296a01bd628ab5f41e4378bc7b1eaa2211b437359be975b4a3e4208bc3f0";
        RawTransaction rawTransaction = TransactionDecoder.decode(hexRaw);
        System.out.println(rawTransaction);
    }

    @Test
    public void sendAssetPublishTransaction() throws Exception {
        String from = testAddress;
        Credentials credentials = Credentials.create(testPrivateKey);
        BigInteger nonce = web3j.aoaGetTransactionCount(from, DefaultBlockParameterName.LATEST)
                                .sendAsync().get().getTransactionCount();


        BigInteger gas = AOAGas.defaultAssetPublishGas.toBigInteger();
        BigInteger gasPrice = AOAGas.defaultGasPrice.toBigInteger();
        BigInteger supply = Convert.toWei("10000", Convert.Unit.AOA).toBigInteger();
        AssetInfo assetInfo = new AssetInfo("yujianAOA", "YU", supply, "yujian token");

        RawTransaction rawTransaction =
            RawTransaction.createAssetPublishTransaction(nonce, gasPrice, gas, assetInfo);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ChainId.MAINNET, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        AOASendTransaction aoaSendTransaction = web3j.aoaSendRawTransaction(hexValue).sendAsync().get();
        if (aoaSendTransaction.getError() != null) {
            System.err.printf("sendRawTransaction error:%s\n", aoaSendTransaction.getError().getMessage());
        } else {
            String transactionHash = aoaSendTransaction.getTransactionHash();
            System.err.printf("sendRawTransaction success,trxHash:%s\n", transactionHash);
        }
    }

    // assetId : EM41941bd1a000215627ff23899c477feb9dfb2bd6
    @Test
    public void sendAssetTransaction() throws Exception {
        String from = testAddress;
        String to = "AOA8ea2354ba012628dd1dad9e44500a70075664a16";
        String assetId = "AOA41941bd1a000215627ff23899c477feb9dfb2bd6";
        Credentials credentials = Credentials.create(testPrivateKey);
        BigInteger nonce = web3j.aoaGetTransactionCount(from, DefaultBlockParameterName.LATEST)
                                .sendAsync().get().getTransactionCount();

        BigInteger gas = AOAGas.defaultTrxGas.toBigInteger();
        BigInteger gasPrice = AOAGas.defaultGasPrice.toBigInteger();

        BigInteger value = Convert.toWei("1.01", Convert.Unit.AOA).toBigInteger();
        RawTransaction rawTransaction = RawTransaction.createAssetTransaction(nonce, gasPrice, gas, to, value, assetId);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ChainId.MAINNET, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        AOASendTransaction aoaSendTransaction = web3j.aoaSendRawTransaction(hexValue).sendAsync().get();
        if (aoaSendTransaction.getError() != null) {
            System.err.printf("sendRawTransaction error:%s\n", aoaSendTransaction.getError().getMessage());
        } else {
            String transactionHash = aoaSendTransaction.getTransactionHash();
            System.err.printf("sendRawTransaction success,trxHash:%s\n", transactionHash);
        }
    }



}
