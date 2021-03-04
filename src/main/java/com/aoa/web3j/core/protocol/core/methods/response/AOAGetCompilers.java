package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;

import java.util.List;

/**
 * aoa_getCompilers.
 */
public class AOAGetCompilers extends Response<List<String>> {
    public List<String> getCompilers() {
        return getResult();
    }
}
