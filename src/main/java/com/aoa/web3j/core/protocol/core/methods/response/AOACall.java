package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;

/**
 * aoa_call.
 */
public class AOACall extends Response<String> {
    public String getValue() {
        return getResult();
    }
}
