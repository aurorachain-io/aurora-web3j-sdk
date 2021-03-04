package com.aoa.web3j;

import com.aoa.web3j.abi.FunctionEncoder;
import com.aoa.web3j.abi.datatypes.Address;
import com.aoa.web3j.abi.datatypes.Function;
import com.aoa.web3j.abi.datatypes.generated.Uint256;
import com.aoa.web3j.core.protocol.Web3j;
import com.aoa.web3j.core.protocol.core.DefaultBlockParameterName;
import com.aoa.web3j.core.protocol.core.methods.response.AOASendTransaction;
import com.aoa.web3j.core.protocol.http.HttpService;
import com.aoa.web3j.core.token.Hello_sol_hello;
import com.aoa.web3j.core.tx.ChainId;
import com.aoa.web3j.core.utils.AOAGas;
import com.aoa.web3j.crypto.Credentials;
import com.aoa.web3j.crypto.ECKeyPair;
import com.aoa.web3j.crypto.Keys;
import com.aoa.web3j.crypto.RawTransaction;
import com.aoa.web3j.crypto.TransactionEncoder;
import com.aoa.web3j.utils.Convert;
import com.aoa.web3j.utils.Numeric;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author yujian    2020/05/21
 */
public class ContractTests {

    private Web3j web3j;

    @Before
    public void init() {
        web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));
    }


    private final String testPrivateKey = "b968d08d67b3664bebd6b11b517557a29186e3c5f8474959d5db7cc0442b1831";
    private final String testAddress = "AOA2e25d6f13330163134b9e321491ca0d45233e054";


    @Test
    public void deploySimpleContract() throws Exception {

        String ownAddress = "0xD1c82c71cC567d63Fd53D5B91dcAC6156E5B96B3";
        String toAddress = "0x6e27727bbb9f0140024a62822f013385f4194999";
        Credentials credentials = Credentials.create(testPrivateKey);
        BigInteger gasLimit = BigInteger.valueOf(186440);
        //部署智能合约
        Hello_sol_hello helloSol =
            Hello_sol_hello.deploy(web3j, credentials, AOAGas.defaultGasPrice.toBigInteger(), gasLimit).send();
        System.out.println(helloSol.getContractAddress());
        //调用智能合约
        System.out.println(helloSol.main(new BigInteger("2")).send());

    }

    @Test
    public void callErc20Contract() throws Exception {
        String contractAddress = "AOA39cd03c1021de42505d0d7acbcece7363afa3433";
        BigInteger gas = AOAGas.defaultCallErc20Gas.toBigInteger();
        BigInteger gasPrice = AOAGas.defaultGasPrice.toBigInteger();
        String to = "AOA6e27727bbb9f0140024a62822f013385f4194999";
        Credentials credentials = Credentials.create(testPrivateKey);

        BigInteger nonce = web3j.aoaGetTransactionCount(testAddress, DefaultBlockParameterName.LATEST)
                                .sendAsync().get().getTransactionCount();
        BigInteger value = Convert.toWei("10", Convert.Unit.WEI).toBigInteger();

        Function function = new Function("transfer", Arrays.asList(new Address(to), new Uint256(value)), Collections
            .emptyList());
        String encodedFunction = FunctionEncoder.encode(function);

        RawTransaction rawTransaction = RawTransaction
            .createFunctionCallTransaction(nonce, gasPrice, gas, contractAddress, BigInteger.valueOf(0),
                                           encodedFunction);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ChainId.MAINNET, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        System.err.printf("sign Hex:%s\n", hexValue);
        AOASendTransaction aoaSendTransaction = web3j.aoaSendRawTransaction(hexValue).sendAsync().get();
        if (aoaSendTransaction.getError() != null) {
            System.err.printf("sendRawTransaction error:%s\n", aoaSendTransaction.getError().getMessage());
        } else {
            String transactionHash = aoaSendTransaction.getTransactionHash();
            System.err.printf("sendRawTransaction success,trxHash:%s\n", transactionHash);
        }

    }
}
