package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;
/**
 * shh_version.
 */
public class ShhVersion extends Response<String> {

    public String getVersion() {
        return getResult();
    }
}
