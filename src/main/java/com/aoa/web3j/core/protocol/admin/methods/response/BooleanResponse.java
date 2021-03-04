package com.aoa.web3j.core.protocol.admin.methods.response;


import com.aoa.web3j.core.protocol.core.Response;

/**
 * Boolean response type.
 */
public class BooleanResponse extends Response<Boolean> {
    public boolean success() {
        return getResult();
    }
}
