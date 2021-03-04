package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;

/**
 * aoa_getStorageAt.
 */
public class AOAGetStorageAt extends Response<String> {
    public String getData() {
        return getResult();
    }
}
