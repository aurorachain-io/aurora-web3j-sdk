package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;

/**
 * aoa_sign.
 */
public class AOASign extends Response<String> {
    public String getSignature() {
        return getResult();
    }
}
