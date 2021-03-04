package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;

/**
 * aoa_coinbase.
 */
public class AOACoinbase extends Response<String> {
    public String getAddress() {
        return getResult();
    }
}
