package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;

/**
 * aoa_protocolVersion.
 */
public class AOAProtocolVersion extends Response<String> {
    public String getProtocolVersion() {
        return getResult();
    }
}
