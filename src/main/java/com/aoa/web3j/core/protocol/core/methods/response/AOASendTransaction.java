package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;

/**
 * aoa_sendTransaction.
 */
public class AOASendTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
