package com.aoa.web3j;

import com.aoa.web3j.core.protocol.Web3j;
import com.aoa.web3j.core.protocol.core.DefaultBlockParameterName;
import com.aoa.web3j.core.protocol.core.Request;
import com.aoa.web3j.core.protocol.core.methods.response.AssetInfo;
import com.aoa.web3j.core.protocol.core.methods.response.AOAGetTransactionCount;
import com.aoa.web3j.core.protocol.core.methods.response.AOAGetTransactionReceipt;
import com.aoa.web3j.core.protocol.core.methods.response.AOASendTransaction;
import com.aoa.web3j.core.protocol.core.methods.response.AOATransaction;
import com.aoa.web3j.core.protocol.core.methods.response.Transaction;
import com.aoa.web3j.core.protocol.core.methods.response.TransactionReceipt;
import com.aoa.web3j.core.protocol.http.HttpService;
import com.aoa.web3j.core.utils.AOAGas;
import com.aoa.web3j.utils.Convert;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

/**
 * @author yujian    2020/05/20
 */
public class RpcMethodTransactionTests {

    private Web3j web3j;

    @Before
    public void init() {
        web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));
    }


    /**
     * 交易查询
     * 1. 普通交易 success
     * 2. 注册代理交易 success
     * 3. 投票(加票)交易 success
     * 4. 投票(减票)交易 success
     * 5. 注册多资产交易 success
     * 6. 多资产转账交易 success
     */
    @Test
    public void getTransactionByHash() throws Exception {
        String trxId = "0xd295457a7004b45f12b459405b91cff179613aafa0df7f52279d602edf0563c2";
        Request<?, AOATransaction> request = web3j.aoaGetTransactionByHash(trxId);
        Transaction transaction = request.sendAsync().get().getResult();
        System.out.println(transaction);
        System.out.println(transaction.getTransactionAction());
    }

    @Test
    public void getTransactionReceipt() throws Exception {
        String trxId = "0xd295457a7004b45f12b459405b91cff179613aafa0df7f52279d602edf0563c2";
        Request<?, AOAGetTransactionReceipt> request = web3j.aoaGetTransactionReceipt(trxId);
        TransactionReceipt transactionReceipt = request.sendAsync().get().getResult();
        System.out.println(transactionReceipt);
    }

    @Test
    public void sendEMTransaction() throws Exception {
        // 需节点本地存在该账户
        String from = "AOA8ea2354ba012628dd1dad9e44500a70075664a16";
        String to = "AOA267a2f33c924a3b1d751a86689d4a0667341178b";
        String md5Value = "202cb962ac59075b964b07152d234b70";
        // 子地址
        to += md5Value;
        String transferEmAmount = "1.02";
        Request<?, AOAGetTransactionCount> nonceRequest =
            web3j.aoaGetTransactionCount(from, DefaultBlockParameterName.LATEST);
        BigInteger nonce = nonceRequest.sendAsync().get().getTransactionCount();

        BigInteger value = Convert.toWei(transferEmAmount, Convert.Unit.AOA).toBigInteger();
        BigInteger gas = AOAGas.defaultTrxGas.toBigInteger();
        BigInteger gasPrice = AOAGas.defaultGasPrice.toBigInteger();

        com.aoa.web3j.core.protocol.core.methods.request.Transaction transaction =
            com.aoa.web3j.core.protocol.core.methods.request.Transaction
                .createAOATransaction(from, to, nonce, value, gas, gasPrice);

        AOASendTransaction aoaSendTransaction = web3j.aoaSendTransaction(transaction).sendAsync().get();

        if(aoaSendTransaction.getError() != null) {
            System.err.printf("send trx fail,error:%s\n", aoaSendTransaction.getError().getMessage());
        }else {
            System.err.printf("send trx success,trxHash:%s\n", aoaSendTransaction.getResult());
        }
    }

    // 失败
    @Test
    public void sendAssetPublishTransaction() throws Exception {
        String from = "AOA8ea2354ba012628dd1dad9e44500a70075664a16";

        Request<?, AOAGetTransactionCount> request =
            web3j.aoaGetTransactionCount(from, DefaultBlockParameterName.LATEST);

        BigInteger nonce = request.sendAsync().get().getTransactionCount();
        BigInteger gas = BigInteger.valueOf(100_000);
        BigInteger gasPrice = AOAGas.defaultGasPrice.toBigInteger();

        BigInteger supply = Convert.toWei("10000", Convert.Unit.AOA).toBigInteger();
        AssetInfo assetInfo = new AssetInfo("yujianEM", "YUY", supply, "yujian token");

        com.aoa.web3j.core.protocol.core.methods.request.Transaction transaction =
            com.aoa.web3j.core.protocol.core.methods.request.Transaction
                .createAssetPublishTransaction(from, nonce, gas, gasPrice, assetInfo);

        Request<?, AOASendTransaction> trxRequest = web3j.aoaSendTransaction(transaction);

        AOASendTransaction aoaSendTransaction = trxRequest.sendAsync().get();

        if (aoaSendTransaction.getError() == null) {
            System.err.printf("send trx success,trxHash:%s\n", aoaSendTransaction.getResult());
        } else {
            System.err.printf("send trx fail,error:%s\n", aoaSendTransaction.getError().getMessage());
        }
    }





}
