package com.aoa.web3j.core.tx;



import com.aoa.web3j.core.protocol.Web3j;
import com.aoa.web3j.core.protocol.core.methods.response.AOASendTransaction;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Transaction manager implementation for read-only operations on smart contracts.
 */
public class ReadonlyTransactionManager extends TransactionManager {

    public ReadonlyTransactionManager(Web3j web3j, String fromAddress) {
        super(web3j, fromAddress);
    }

    @Override
    public AOASendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value)
            throws IOException {
        throw new UnsupportedOperationException(
                "Only read operations are supported by this transaction manager");
    }
}
