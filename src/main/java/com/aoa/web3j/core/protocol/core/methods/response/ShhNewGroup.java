package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;

/**
 * shh_newGroup.
 */
public class ShhNewGroup extends Response<String> {

    public String getAddress() {
        return getResult();
    }
}
