package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;

/**
 * aoa_mining.
 */
public class AOAMining extends Response<Boolean> {
    public boolean isMining() {
        return getResult();
    }
}
