package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;

/**
 * aoa_compileLLL.
 */
public class AOACompileLLL extends Response<String> {
    public String getCompiledSourceCode() {
        return getResult();
    }
}
