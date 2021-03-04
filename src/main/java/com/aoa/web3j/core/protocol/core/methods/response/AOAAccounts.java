package com.aoa.web3j.core.protocol.core.methods.response;



import com.aoa.web3j.core.protocol.core.Response;

import java.util.List;

/**
 * aoa_accounts.
 */
public class AOAAccounts extends Response<List<String>> {
    public List<String> getAccounts() {
        return getResult();
    }
}
