package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;

/**
 * aoa_getCode.
 */
public class AOAGetCode extends Response<String> {
    public String getCode() {
        return getResult();
    }
}
